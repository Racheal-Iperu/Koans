package objects;

import io.fries.koans.Koan;

import static io.fries.koans.KoanAssert.__;
import static io.fries.koans.KoanAssert.assertThat;

@SuppressWarnings("all")
class InheritanceKoans {

    abstract class Animal {
        abstract public String makeSomeNoise();
    }

    class Cow extends Animal {

        @Override
        public String makeSomeNoise() {
            return "Moo!";
        }
    }

    class Dog extends Animal {

        @Override
        public String makeSomeNoise() {
            return "Woof!";
        }

        public boolean canFetch() {
            return true;
        }
    }

    class Puppy extends Dog {

        @Override
        public String makeSomeNoise() {
            return "Squeak!";
        }

        public boolean canFetch() {
            return false;
        }
    }

    @Koan
    void method_overloading() {
        Cow bob = new Cow();
        Dog max = new Dog();
        Puppy barney = new Puppy();

        assertThat(bob.makeSomeNoise()).isEqualTo("Moo!");
        assertThat(max.makeSomeNoise()).isEqualTo("Woof!");
        assertThat(barney.makeSomeNoise()).isEqualTo("Squeak!");

        assertThat(max.canFetch()).isEqualTo(true);
        assertThat(barney.canFetch()).isEqualTo(false);
        // assertThat(bob.canFetch()).isEqualTo(__);    // Is it possible? Why?
    }

    @Koan
    void method_overloading_using_polymorphism() {
        Animal bob = new Cow();
        Animal max = new Dog();
        Animal barney = new Puppy();

        assertThat(bob.makeSomeNoise()).isEqualTo("Moo!");
        assertThat(max.makeSomeNoise()).isEqualTo("Woof!");
        assertThat(barney.makeSomeNoise()).isEqualTo("Squeak!");

        //assertThat(max.canFetch()).isEqualTo(__);    // Is it possible? Why?
        // assertThat(barney.canFetch()).isEqualTo(__); // Is it possible? Why?
        // assertThat(bob.canFetch()).isEqualTo(__);    // Is it possible? Why?
    }

    @Koan
    void inheritance_hierarchy() {
        Animal someAnimal = new Cow();
        Animal bob = new Cow();

        assertThat(someAnimal.makeSomeNoise().equals(bob.makeSomeNoise())).isEqualTo(true);
        assertThat(bob instanceof Animal).isEqualTo(true);
        assertThat(bob instanceof Cow).isEqualTo(true);
        assertThat(bob instanceof Puppy).isEqualTo(false);
    }

    @Koan
    void deeper_inheritance_hierarchy() {
        Dog max = new Dog();
        Puppy barney = new Puppy();

        assertThat(max instanceof Puppy).isEqualTo(false);
        assertThat(max instanceof Dog).isEqualTo(true);
        assertThat(barney instanceof Puppy).isEqualTo(true);
        assertThat(barney instanceof Dog).isEqualTo(true);
    }

    abstract class Farm {
        abstract public Animal findAnimal();
    }

    class CowFarm extends Farm {

        public Animal findAnimal() {
            return new Cow();
        }
    }

    @Koan
    void overridden_methods_may_return_subtype() {
        Cow cow = (Cow) new CowFarm().findAnimal(); // What do you need to change in order to get rid of this type cast?
        assertThat(cow instanceof Cow).isEqualTo(true);
    }

    @Koan
    void a_subclass_will_always_call_its_parent_constructor_before_its_own() {
        class Parent {
            String result = "a";

            Parent() {
                result += "x";
            }
        }

        class Child extends Parent {
            Child() {
                result += "g";
            }
        }

        assertThat(new Child().result).isEqualTo("axg");
    }

    @Koan
    void a_subclass_constructor_can_choose_which_parent_constructor_to_call_when_multiple_are_defined() {
        class Parent {
            String result = "a";

            Parent() {
                result += "x";
            }

            Parent(String value) {
                result += value;
            }
        }

        class Child extends Parent {
            Child() {
                super("Boo");
                result += "g";
            }
        }

        assertThat(new Child().result).isEqualTo("aBoog");
    }
}
