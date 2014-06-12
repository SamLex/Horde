package uk.samlex.horde.config;

public enum HordeType {

    RANDOM {
        @Override
        public String toString() {
            return "random";
        }
    },
    WAVE {
        @Override
        public String toString() {
            return "wave";
        }
    },
    GROUP {
        @Override
        public String toString() {
            return "group";
        }
    }
}
