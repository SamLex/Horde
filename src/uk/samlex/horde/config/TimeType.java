package uk.samlex.horde.config;

public enum TimeType {

    INGAME {
        @Override
        public String toString() {
            return "ingame";
        }
    },
    REALTIME {
        @Override
        public String toString() {
            return "realtime";
        }
    }
}
