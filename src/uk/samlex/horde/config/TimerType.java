package uk.samlex.horde.config;

public enum TimerType {

    CONSTANT {
        @Override
        public String toString() {
            return "constant";
        }
    },
    START {
        @Override
        public String toString() {
            return "start";
        }
    },
    ONETIME {
        @Override
        public String toString() {
            return "onetime";
        }
    }
}
