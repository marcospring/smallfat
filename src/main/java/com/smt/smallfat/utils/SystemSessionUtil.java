package com.smt.smallfat.utils;

import com.csyy.common.JSONUtils;
import com.csyy.common.UUIDProvider;
import com.csyy.core.exception.CommonException;
import com.csyy.redis.api.RedisSession;
import com.csyy.redis.factory.RedisSessionFactory;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.vo.LoginVO;
import com.smt.smallfat.vo.SysUserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * Created by xindongwang on 17/3/14.
 */
@Service
public class SystemSessionUtil {

    static Logger logger = LoggerFactory.getLogger(SystemSessionUtil.class);

    /**
     * 用户会话过期时间，单位：秒
     */
    private static int SESSION_TIMEOUT_SECONDS = 28 * 60;
    public static final String MANAGE_USERSESSION_REDIS_HEAD = "MANAGE_USERSESSION:";
    public static final String MANAGE_USERSESSION_UUID_HEAD = "MANAGE_USERSESSION_UUID:";

    @Autowired
    private RedisSessionFactory redisSessinoFactory;


    private static RedisSession redisSession;

    @PostConstruct
    public void start(){
        redisSession = redisSessinoFactory.getSession();
    }


    /**
     * 创建并存系统用户登陆后到mailiSid
     *
     * @param loginVO
     * @return
     */
    public static LoginVO createSysUserSession(LoginVO loginVO) {
        String localSessionId = UUIDProvider.uuid();
        loginVO.setSession(localSessionId);
        saveOrUpdate(localSessionId, loginVO);
        saveUserUuidSession(loginVO.getUser(),localSessionId);
        return loginVO;
    }


    /**
     * 保存uuid与用户session的关系
     * @param sysUserVo
     * @param localSessionId
     */
    public static void saveUserUuidSession(SysUserVo sysUserVo, String localSessionId){
        StringBuilder key=new StringBuilder(MANAGE_USERSESSION_UUID_HEAD);
        key.append(sysUserVo.getUuid());
        redisSession.setData(key.toString(),localSessionId);
    }


    public static void logoutByLocalSessionId(String localSessionId){
        StringBuilder key = new StringBuilder(MANAGE_USERSESSION_REDIS_HEAD);
        redisSession.delete(key.append(localSessionId).toString());
    }

    /**
     * 根据系统用户session获取用户Id
     *
     * @param localSessionId
     * @return
     */
    public static Integer getUserId(String localSessionId) {
        Integer uid =  getAndRefashUserSession(localSessionId).getUser().getId();
        logger.debug("maili_Sid:"+localSessionId+"=================uid:"+uid);
        return uid;
    }



    /**
     * 获取用户会话对象
     *
     * @param localSessionId
     * @return
     */
    public static LoginVO getAndRefashUserSession(String localSessionId) {
        LoginVO loginVO = redisSession.getTypeObject(LoginVO.class,
                MANAGE_USERSESSION_REDIS_HEAD + localSessionId);
        if (loginVO != null) {
            // 是否多处登录
            String sessionId = redisSession.getData(MANAGE_USERSESSION_UUID_HEAD
                    + loginVO.getUser().getUuid());
            if (!localSessionId.equals(sessionId)) {
                redisSession.delete(MANAGE_USERSESSION_REDIS_HEAD + localSessionId);
                throw new CommonException(ResultConstant.SysUserResult.LOGOIN_BY_OTHER);
            }
            // 重新存一次，延迟时间
            saveOrUpdate(localSessionId, loginVO);
            return loginVO;
        } else {
            throw new CommonException(ResultConstant.SysUserResult.SESSION_LOST_ERROR);
        }
    }

    /**
     * 保存或更新会话信息，延长过期时间
     *
     * @param localSessionId
     * @param loginVO
     */
    private static void saveOrUpdate(String localSessionId,
                                     LoginVO loginVO) {
        redisSession.setData(MANAGE_USERSESSION_REDIS_HEAD + localSessionId,
                JSONUtils.toJson(loginVO), SESSION_TIMEOUT_SECONDS);
    }

}
