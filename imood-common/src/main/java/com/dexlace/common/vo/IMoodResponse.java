package com.dexlace.common.vo;

import java.util.HashMap;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/6/30
 */
public class IMoodResponse extends HashMap<String, Object> {


        private static final long serialVersionUID = -8713837118340960775L;

        public IMoodResponse message(String message) {
            this.put("message", message);
            return this;
        }

        public IMoodResponse data(Object data) {
            this.put("data", data);
            return this;
        }

        @Override
        public IMoodResponse put(String key, Object value) {
            super.put(key, value);
            return this;
        }

        public String getMessage() {
            return String.valueOf(get("message"));
        }

        public Object getData() {
            return get("data");
        }

}
