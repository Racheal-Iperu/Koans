package collections;

import io.fries.koans.Koan;
import io.fries.koans.collections.*;
import org.junit.jupiter.api.Nested;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static io.fries.koans.KoanAssert.isLambda;
import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("InnerClassMayBeStatic")
class  StreamsKoans extends OnlineStore {

    @Nested
    class Part1 {

        /**
         * Create a {@link Stream} from customerList only including customer who has more budget than 10000.
         * Use lambda expression for Predicate and {@link Stream#filter} for filtering.
         */
        @Koan
        void find_rich_customers() {
            List<Customer> customerList = mall.getCustomers();

            Predicate<Customer> richCustomerCondition = p -> p.budget> 10000;
            Stream<Customer> richCustomerStream =customerList.stream();

            assertThat(isLambda(richCustomerCondition)).isTrue();
            List<Customer> richCustomer = richCustomerStream.filter(richCustomerCondition).collect(Collectors.toList());

            assertThat(richCustomer).hasSize(2);
            assertThat(richCustomer).contains(customerList.get(3), customerList.get(7));
        }

        /**
         * Create a {@link Stream} from customerList with age values.
         * Use method reference(best) or lambda expression(okay) for creating {@link Function} which will
         * convert {@link Customer} to {@link Integer}, and then apply it by using {@link Stream#map}.
         */
        @Koan
        void how_old_are_the_customers() {
            List<Customer> customerList = mall.getCustomers();

            Function<Customer, Integer> getAgeFunction = Customer::getAge;
            Stream<Integer> ageStream = customerList.stream().map(getAgeFunction);

            assertThat(isLambda(getAgeFunction)).isTrue();
            List<Integer> ages = ageStream.collect(Collectors.toList());
            assertThat(ages).hasSize(10);
            assertThat(ages).contains(22, 27, 28, 38, 26, 22, 32, 35, 21, 36);
        }
    }

    @Nested
    class Part2 {

        /**
         * Create a stream with ascending ordered age values.
         * Use {@link Stream#sorted} to sort them.
         */
        @Koan
        void sort_by_age() {
            List<Customer> customerList = mall.getCustomers();

            Stream<Integer> sortedAgeStream = customerList.stream().map(Customer::getAge).sorted();

            List<Integer> sortedAgeList = sortedAgeStream.collect(Collectors.toList());
            assertThat(sortedAgeList).contains(21, 22, 22, 26, 27, 28, 32, 35, 36, 38);
        }

        /**
         * Create a stream with descending ordered age values.
         */
        @Koan
        void desc_sort_by_age() {
            List<Customer> customerList = mall.getCustomers();

            Comparator<Integer> descOrder =Comparator.reverseOrder();
            Stream<Integer> sortedAgeStream = customerList.stream().map(Customer::getAge).sorted(descOrder);

            assertThat(isLambda(descOrder)).isFalse();
            List<Integer> sortedAgeList = sortedAgeStream.collect(Collectors.toList());
            assertThat(sortedAgeList).contains(38, 36, 35, 32, 28, 27, 26, 22, 22, 21);
        }

        /**
         * Create a stream with top 3 rich customers using {@link Stream#limit} to limit the size of the stream.
         */
        @Koan
        void top_3_rich_customer() {
            List<Customer> customerList = mall.getCustomers();
          //  List rich = customerList.stream().filter(p->p.budget>8000).map(Customer::getName).collect(Collectors.toList());
          //  System.out.println(rich);
            Stream<String> top3RichCustomerStream = customerList.stream().filter(p->p.budget>8000).map(Customer::getName);

           List<String> top3RichCustomerList = top3RichCustomerStream.collect(Collectors.toList());
          assertThat(top3RichCustomerList).contains("Diana", "Andrew", "Chris");
        }

        /**
         * Create a stream with distinct age values using {@link Stream#distinct}.
         */
        @Koan
        void distinct_age() {
            List<Customer> customerList = mall.getCustomers();

            Stream<Integer> distinctAgeStream = customerList.stream().map(Customer::getAge).distinct();

            List<Integer> distinctAgeList = distinctAgeStream.collect(Collectors.toList());
            assertThat(distinctAgeList).contains(22, 27, 28, 38, 26, 32, 35, 21, 36);
        }

        /**
         * Create a stream with items' names stored in {@code Customer.wantsToBuy}.
         * Use {@link Stream#flatMap} to create a stream from each element of a stream.
         */
        @Koan
        void items_customers_want_to_buy() {
            List<Customer> customerList = mall.getCustomers();
             //List<Item> items=customerList.stream().flatMap(p->p.wantsToBuy.stream()).collect(Collectors.toList());
             //System.out.println(items.get(items.size()));

            Function<Customer, Stream<Item>> getItemStream =p->p.getWantsToBuy().stream();
            Stream<String> itemStream = customerList.stream().flatMap(p->{
                return p.getWantsToBuy().stream().map(Item::getName);
            });


            assertThat(isLambda(getItemStream)).isTrue();
            List<String> itemList = itemStream.collect(Collectors.toList());
            assertThat(itemList).contains(
                    "small table", "plate", "fork", "ice cream", "screwdriver", "cable", "earphone", "onion",
                    "ice cream", "crisps", "chopsticks", "cable", "speaker", "headphone", "saw", "bond",
                    "plane", "bag", "cold medicine", "chair", "desk", "pants", "coat", "cup", "plate", "fork",
                    "spoon", "ointment", "poultice", "spinach", "ginseng", "onion"
            );
        }
    }

    @Nested
    class Part3 {

        /**
         * Count how many items there are in {@code Customer.wantsToBuy} using {@link Stream#count}.
         */
        @Koan
        void how_many_items_wanted() {
            List<Customer> customerList = mall.getCustomers();

           // long sum = customerList.stream().map(Customer::getWantsToBuy).count();// has to be 32
            long sum= customerList.stream().flatMap(p->{
                return p.getWantsToBuy().stream().map(Item::getName);
            }).count();
            assertThat(sum).isEqualTo(32L);
        }

        /**
         * Find the richest customer's budget by using {@link Stream#max} and {@link Comparator#naturalOrder}.
         * Don't use {@link Stream#sorted}.
         */
        @Koan
        void richest_customer() {
            List<Customer> customerList = mall.getCustomers();

            Comparator<Integer> comparator = Comparator.naturalOrder();
            Optional<Integer> richestCustomer = customerList.stream().map(Customer::getBudget).max(comparator);

            assertThat(comparator.getClass().getSimpleName()).isEqualTo("NaturalOrderComparator");
            assertThat(richestCustomer.get()).isEqualTo(12000);
        }

        /**
         * Find the youngest customer by using {@link Stream#min}.
         * Don't use {@link Stream#sorted}.
         */
        @Koan
        void youngest_customer() {
            List<Customer> customerList = mall.getCustomers();
           // System.out.println(customerList.stream().map(Customer::getAge).min(Comparator.comparing(Integer::intValue)).);
            Comparator<Customer> comparator = Comparator.comparingInt(Customer::getAge);

            Optional<Customer> youngestCustomer = customerList.stream().min(Comparator.comparingInt(Customer::getAge));
            assertThat(youngestCustomer.get()).isEqualTo(customerList.get(8));
        }
    }

    @Nested
    class Part4 {

        /**
         * Find the first customer who registered this online store by using {@link Stream#findFirst}.
         * The customerList are ascending ordered by registered timing.
         */
        @Koan
        void first_registrant() {
            List<Customer> customerList = mall.getCustomers();

            Optional<Customer> firstCustomer = customerList.stream().findFirst();

            assertThat(firstCustomer.get()).isEqualTo(customerList.get(0));
        }

        /**
         * Check whether any customer older than 40 exists or not, by using {@link Stream#anyMatch}.
         */
        @Koan
        void is_there_anyone_older_than_40() {
            List<Customer> customerList = mall.getCustomers();

            boolean olderThan40Exists = customerList.stream().anyMatch(p->p.getAge()>40);

            assertThat(olderThan40Exists).isFalse();
        }

        /**
         * Check whether all customer are older than 20 or not, by using {@link Stream#allMatch}.
         */
        @Koan
        void is_everybody_older_than_20() {
            List<Customer> customerList = mall.getCustomers();

            boolean allOlderThan20 = customerList.stream().allMatch(p->p.getAge()>20);

            assertThat(allOlderThan20).isTrue();
        }

        /**
         * Confirm that none of the customer has empty list for their {@code Customer.wantsToBuy}
         * by using {@link Stream#noneMatch}.
         */
        @Koan
        void everyone_wants_something() {
            List<Customer> customerList = mall.getCustomers();

            boolean everyoneWantsSomething =customerList.stream().noneMatch(p->p.wantsToBuy.isEmpty());

            assertThat(everyoneWantsSomething).isTrue();
        }
    }

    @Nested
    class Part5 {

        /**
         * Create a list of customer names by using {@link Stream#collect} and {@link Collectors#toList}.
         */
        @Koan
        void name_list() {
            List<Customer> customerList = mall.getCustomers();

            List<String> nameList =customerList.stream().map(Customer::getName).collect(Collectors.toList());

            assertThat(nameList).contains(
                    "Joe", "Steven", "Patrick", "Diana", "Chris", "Kathy", "Alice", "Andrew", "Martin", "Amy"
            );
        }

        /**
         * Create a set of customer age by using {@link Stream#collect} and {@link Collectors#toSet}.
         */
        @Koan
        void age_set() {
            List<Customer> customerList = mall.getCustomers();

            Set<Integer> ageSet =customerList.stream().map(Customer::getAge).collect(Collectors.toSet()) ;

            assertThat(ageSet).hasSize(9);
            assertThat(ageSet).contains(21, 22, 26, 27, 28, 32, 35, 36, 38);
        }

        /**
         * Create a csv string of customer names in brackets "[]" by using {@link Collectors#joining}.
         */
        @Koan
        void name_in_csv() {
            List<Customer> customerList = mall.getCustomers();

            String string = customerList.stream().map(Customer::getName).collect(Collectors.joining(( ","),("[") ,"]"));

            assertThat(string).isEqualTo("[Joe,Steven,Patrick,Diana,Chris,Kathy,Alice,Andrew,Martin,Amy]");
        }

        /**
         * Get the oldest customer by using {@link Collectors#maxBy}.
         * Don't use any intermediate operations.
         */
        @Koan
        void oldest_customer() {
            List<Customer> customerList = mall.getCustomers();

            Optional<Customer> oldestCustomer = customerList.stream().collect(Collectors.maxBy(Comparator.comparing(p->p.age)));

            assertThat(oldestCustomer.get()).isEqualTo(customerList.get(3));
        }

        /**
         * Create a map of age as key and number of customers as value using {@link Collectors#groupingBy}
         * and {@link Collectors#counting}.
         */
        @Koan
        void age_distribution() {
            List<Customer> customerList = mall.getCustomers();

            Map<Integer, Long> ageDistribution =customerList.stream().collect(Collectors.groupingBy(Customer::getAge,Collectors.counting()));

            assertThat(ageDistribution).hasSize(9);
            ageDistribution.forEach((age, numberOfCustomers) -> {
                if (age.equals(22)) {
                    assertThat(numberOfCustomers).isEqualTo(2L);
                } else {
                    assertThat(numberOfCustomers).isEqualTo(1L);
                }
            });
        }
    }

    @Nested
    class Part6 {

        /**
         * Create a stream with string values "a" "b" "c" by using {@link Stream#of}.
         */
        @Koan
        void stream_from_values() {
            Stream<String> abcStream = Stream.of("a", "b" ,"c");

            List<String> abcList = abcStream.collect(Collectors.toList());
            assertThat(abcList).contains("a", "b", "c");
        }

        /**
         * Create a stream only with multiples of 3, starting from 0, size of 10, by using {@link Stream#iterate}.
         */
        @Koan
        void number_stream() {
            Stream<Integer> numbers = Stream.iterate(0,p->p+1).filter(p->p%3==0).limit(10);

            List<Integer> numbersList = numbers.collect(Collectors.toList());
            assertThat(numbersList).contains(0, 3, 6, 9, 12, 15, 18, 21, 24, 27);
        }
    }

    @Nested
    class Part7 {

        /**
         * Create {@link IntStream} with customer ages by using {@link Stream#mapToInt}.
         * Then calculate the average of ages by using {@link IntStream#average}.
         */
        @Koan
        void average_age() {
            List<Customer> customerList = mall.getCustomers();

            IntStream ageStream =customerList.stream().mapToInt(Customer::getAge);
            OptionalDouble average = ageStream.average();

            assertThat(average.getAsDouble()).isEqualTo(28.7);
        }

        /**
         * Create {@link LongStream} with all items' prices using {@link Stream#mapToLong}.
         * Then calculate the sum of prices using {@link LongStream#sum}.
         */
        @Koan
        void how_much_to_buy_all_items() {
            List<Shop> shopList = mall.getShops();
//System.out.println(shopList.stream().map(shop-> {
//    return shop.getItems().stream().map(Item::getPrice);
//}).collect(Collectors.toList()));
//System.out.println(shopList.stream().map(Shop::getItems).collect(Collectors.toList()));
            LongStream priceStream = shopList.stream().flatMapToLong(p->{
                return p.getItems().stream().mapToLong(Item::getPrice);
            });

            long priceSum = priceStream.sum();
            System.out.println(priceSum);

            assertThat(priceSum).isEqualTo(60930L);
        }
    }

    @Nested
    class Part8 {

        /**
         * Create a set of item names that are in {@code Customer.wantsToBuy} but not on sale in any shop.
         */
        @Koan
        void items_not_on_sale() {
            Stream<Customer> customerStream = mall.getCustomers().stream();
            Stream<Shop> shopStream = mall.getShops().stream();

           // System.out.println(customerStream.m);

            List<String> itemListOnSale =shopStream.flatMap(p->{
                return p.getItems().stream().map(Item::getName);
            }).collect(Collectors.toList());
            Set<String> itemSetNotOnSale = customerStream.flatMap(p->{
                return p.getWantsToBuy().stream().map(Item::getName).filter(Predicate.not(itemListOnSale::contains));
            }).collect(Collectors.toSet());

            assertThat(itemSetNotOnSale).hasSize(3);
            assertThat(itemSetNotOnSale).contains("bag", "pants", "coat");
        } 

        /**
         * Create a customer's name list including who are having enough money to buy all items they want which is
         * on sale.
         * Items that are not on sale can be counted as 0 money cost.
         * If there is several same items with different prices, customer can choose the cheapest one.
         */
        @Koan
        void having_enough_money() {
            Stream<Customer> customerStream = mall.getCustomers().stream();
            Stream<Shop> shopStream = mall.getShops().stream();
            List<Item> onSale = shopStream.flatMap(shop -> shop.getItems().stream()).collect(Collectors.toList());

            Predicate<Customer> havingEnoughMoney =   customer -> customer.getBudget() >= customer.getWantsToBuy().stream().mapToInt(
                    wantedItem -> onSale.stream().filter(shopItem -> shopItem.getName().equals(wantedItem.getName())).min(Comparator.comparingInt(Item::getPrice)).map(Item::getPrice).orElse(0)
            ).sum();
            List<String> customerNameList = customerStream.filter(havingEnoughMoney)
                    .map(Customer::getName).collect(Collectors.toList());

            assertThat(customerNameList).hasSize(7);
            assertThat(customerNameList).contains("Joe", "Patrick", "Chris", "Kathy", "Alice", "Andrew", "Amy");
        }
    }

    @Nested
    class Part9 {

        /**
         * Implement a {@link Collector} which can create a String with comma separated names shown in the assertion.
         * The collector will be used by serial stream.
         */
        @Koan
        void simplest_string_join() {
            List<Customer> customerList = mall.getCustomers();

            Supplier<StringJoiner> supplier = ()->new StringJoiner(",", "", "");

            BiConsumer<StringJoiner, String> accumulator = StringJoiner::add;
            BinaryOperator<StringJoiner> combiner =null;
            Function<StringJoiner, String> finisher = StringJoiner::toString;

           Collector<String, ?, String> toCsv = new SimpleCollector<> (
                   supplier,
                   accumulator,
                   combiner,
                   finisher,
                   Collections.emptySet()
            );
            String nameAsCsv = customerList.stream().map(Customer::getName).collect(toCsv);
           assertThat(nameAsCsv).isEqualTo("Joe,Steven,Patrick,Diana,Chris,Kathy,Alice,Andrew,Martin,Amy");
        }

        /**
         * Implement a {@link Collector} which can create a {@link Map} with keys as item and values as {@link Set}
         * of customers who are wanting to buy that item.
         * The collector will be used by parallel stream.
         */
        @Koan
        void map_keyed_by_items() {
            List<Customer> customerList = mall.getCustomers();

            Supplier<Map<String,Set<String>>> supplier = HashMap::new;
            BiConsumer<Map<String,Set<String>>, Customer> accumulator = (map, customer)->{
                customer.getWantsToBuy().forEach(
                        item->{
                            if(map.containsKey(item.getName())){
                                map.get(item.getName()).add(customer.getName());
                            }else{
                                map.put(item.getName(),Stream.of(customer.getName()).collect(Collectors.toSet()));
                            }
                        }
                );
            };
            BinaryOperator<Map<String, Set<String>>> combiner = (left, right) -> {
                right.forEach((item, customers) -> {
                    left.merge(item, customers, (oldVal, newVal)->{
                        oldVal.addAll(newVal);
                        return oldVal;
                    });
                });
                return left;
            };
            Function<Map<String, Set<String>>, Map<String, Set<String>>> finisher = null;


            Collector<Customer, ?, Map<String, Set<String>>> toItemAsKey = new SimpleCollector<>(
                    supplier,
                    accumulator,
                    combiner,
                    finisher,
                    EnumSet.of(CONCURRENT, IDENTITY_FINISH)
            );
            Map<String, Set<String>> itemMap = customerList.stream().parallel().collect(toItemAsKey);
            assertThat(itemMap.get("plane")).containsExactlyInAnyOrder("Chris");
            assertThat(itemMap.get("onion")).containsExactlyInAnyOrder("Patrick", "Amy");
            assertThat(itemMap.get("ice cream")).containsExactlyInAnyOrder("Patrick", "Steven");
            assertThat(itemMap.get("earphone")).containsExactlyInAnyOrder("Steven");
            assertThat(itemMap.get("plate")).containsExactlyInAnyOrder("Joe", "Martin");
            assertThat(itemMap.get("fork")).containsExactlyInAnyOrder("Joe", "Martin");
            assertThat(itemMap.get("cable")).containsExactlyInAnyOrder("Diana", "Steven");
            assertThat(itemMap.get("desk")).containsExactlyInAnyOrder("Alice");
        }

        /**
         * Create a {@link String} of "n"th bit ON.
         * For example:
         * "3" will be "001"
         * "1,3,5" will be "10101"
         * "1-3" will be "111"
         * "7,1-3,5" will be "1110101"
         */
        @Koan
        void bit_list_to_bit_string() {
            String bitList = "22-24,9,42-44,11,4,46,14-17,5,2,38-40,33,50,48";

            Collector<String, ?, String> toBitString = new Collector<String, List<Integer>, String>() {
                @Override public Supplier<List<Integer>> supplier() {
                    return ArrayList::new;
                }

                @Override public BiConsumer<List<Integer>, String> accumulator() {
                    return (list, str) -> {
                        List<String> splitString = Arrays.asList(str.split("-"));
                        List<Integer> splitInt = splitString.stream().map(Integer::valueOf).collect(Collectors.toList());
                        if (splitInt.size() > 1) {
                            list.addAll(Stream.iterate(splitInt.get(0), e -> ++e)
                                    .limit(splitInt.get(1) - splitInt.get(0) + 1)
                                    .collect(Collectors.toList()));
                        } else {
                            list.add(splitInt.get(0));
                        }
                    };
                }

                @Override public BinaryOperator<List<Integer>> combiner() {
                    return null;
                }

                @Override public Function<List<Integer>, String> finisher() {
                    return list -> {
                        long max = list.stream().max(Comparator.naturalOrder()).get();
                        return list.stream().distinct().collect(
                                new Collector<Integer, List<String>, String>() {
                                    @Override public Supplier<List<String>> supplier() {
                                        return () -> Stream.generate(() -> "0")
                                                .limit(max)
                                                .collect(Collectors.toList());
                                    }

                                    @Override public BiConsumer<List<String>, Integer> accumulator() {
                                        return (strList, nth) -> strList.set(nth - 1, "1");
                                    }

                                    @Override public BinaryOperator<List<String>> combiner() {
                                        return null;
                                    }

                                    @Override public Function<List<String>, String> finisher() {
                                        return strList -> strList.stream().collect(Collectors.joining());
                                    }

                                    @Override public Set<Characteristics> characteristics() {
                                        return Collections.emptySet();
                                    }
                                });
                    };
                }

                @Override public Set<Characteristics> characteristics() {
                    return Collections.emptySet();
                }
            };

            String bitString = Arrays.stream(bitList.split(",")).collect(toBitString);
           assertThat(bitString).isEqualTo("01011000101001111000011100000000100001110111010101");
        }
    }
}
