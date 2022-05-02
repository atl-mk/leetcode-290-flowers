import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toCollection;

class Solution {
    int[] fullBloomFlowers(final int[][] flowers, final int[] people) {
        final List<Person> unsortedPeopleList = new ArrayList<>();
        for (int personId = 0; personId < people.length; personId++) {
            unsortedPeopleList.add(new Person(personId, people[personId]));
        }
        final LinkedList<Person> peopleList = unsortedPeopleList.stream()
                .sorted(new PersonObservationTimeComparator())
                .collect(toCollection(LinkedList::new));
        final LinkedList<Flower> flowerList = stream(flowers)
                .map(flower -> new Flower(flower[0], flower[1]))
                .sorted(new FlowerStartTimeComparator())
                .collect(toCollection(LinkedList::new));

        int numInBloom = 0;
        final int[] observed = new int[people.length];
        final Map<Integer, Integer> flowersInBloom = new HashMap<>();
        for (int time = 0; !peopleList.isEmpty(); time++) {
            while (!flowerList.isEmpty() && flowerList.peekFirst().start == time) {
                final int endTime =  flowerList.pop().end;
                flowersInBloom.putIfAbsent(endTime, 0);
                flowersInBloom.put(endTime, flowersInBloom.get(endTime) + 1);
                numInBloom++;
            }

            while (!peopleList.isEmpty() && peopleList.peekFirst().observationTime == time) {
                observed[peopleList.pop().id] = numInBloom;
            }

            numInBloom -= ofNullable(flowersInBloom.remove(time)).orElse(0);
        }

        return observed;
    }

    static class FlowerStartTimeComparator implements Comparator<Flower> {
        @Override
        public int compare(Flower flower1, Flower flower2) {
            return Integer.compare(flower1.start, flower2.start);
        }
    }

    static class PersonObservationTimeComparator implements Comparator<Person> {
        @Override
        public int compare(Person person1, Person person2) {
            return Integer.compare(person1.observationTime, person2.observationTime);
        }
    }

    record Flower(int start, int end) {}

    record Person(int id, int observationTime) {}
}
