package com.iqexception.fxhelper.support;

public class TLVarManager {

    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();


    public static User getCurrentUser() {
        return currentUser.get();
    }

    public static void setCurrentUser(User currentUser) {
        TLVarManager.currentUser.set(currentUser);
    }

    public static void removeCurrentUser() {
        TLVarManager.currentUser.remove();
    }

    public static class User {
        private Long userId;
        private String openId;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public User(Long userId, String openId) {
            this.userId = userId;
            this.openId = openId;
        }
    }
}
