   Public final class Person implements Savable {
        private final String name;
        private final int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String toString() {
            return "Person(" + name + ", " + age + ")";
        }

        public String id() {
            return name;
        }

        public String name() {
            return name;
        }

        public int age() {
            return age;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Person) obj;
            return Objects.equals(this.name, that.name) && this.age == that.age;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }

        @Override
        public String getId() {
            return name;
        }
    }
