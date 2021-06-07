package kr.co.player.api.infrastructure.interceptor;

import kr.co.player.api.infrastructure.persistence.entity.UserEntity;

public class UserThreadLocal {

    private static final ThreadLocal<UserEntity> threadLocal;

    static {
        threadLocal = new ThreadLocal<>();
    }

    public static void set(UserEntity userEntity) {
        threadLocal.set(userEntity);
    }

    public static void remove() {
        threadLocal.remove();
    }

    public static UserEntity get() {
        return threadLocal.get();
    }
}
