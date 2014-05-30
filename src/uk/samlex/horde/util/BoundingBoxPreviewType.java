package uk.samlex.horde.util;

public enum BoundingBoxPreviewType {
    CORNERS {
        @Override
        public String toString() {
            return "corners";
        }
    },
    FILL {
        @Override
        public String toString() {
            return "fill";
        }
    },
    HOLLOW {
        @Override
        public String toString() {
            return "hollow";
        }
    },
    OUTLINE {
        @Override
        public String toString() {
            return "outline";
        }
    },
}
