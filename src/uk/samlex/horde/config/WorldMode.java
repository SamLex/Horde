package uk.samlex.horde.config;

public enum WorldMode {

    NONE {
        @Override
        public String toString() {
            return "none";
        }
    },
    GLOBAL {
        @Override
        public String toString() {
            return "global";
        }
    },
    SAFE {
        @Override
        public String toString() {
            return "safe";
        }
    },
    UNSAFE {
        @Override
        public String toString() {
            return "unsafe";
        }
    }
}
