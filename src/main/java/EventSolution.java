import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.concat;

class EventSolution {
    int[] fullBloomFlowers(final int[][] flowers, final int[] people) {
        final List<Event> events = concat(
                concat(stream(flowers).map(flower -> new FlowerEvent(flower[0], 1)),
                        stream(flowers).map(flower -> new FlowerEvent(flower[1], -1))),
                range(0, people.length).mapToObj(i -> new PersonObserverEvent(people[i], i)))
                .sorted(new EventComparator())
                .collect(Collectors.toList()); // can't simplify by using #toList(), breaks casting

        int numInBloom = 0;
        final int[] observed = new int[people.length];
        for (Event event : events) {
            numInBloom += event.action(numInBloom, observed);
        }

        return observed;
    }

    interface Event {
        int time();

        int action(final int numInBloom, final int[] observed);
    }

    record FlowerEvent(int time, int diff) implements Event {
        @Override
        public int action(int numInBloom, int[] observed) {
            return diff;
        }
    }

    record PersonObserverEvent(int time, int index) implements Event {
        @Override
        public int action(int numInBloom, int[] observed) {
            observed[index] = numInBloom;
            return 0;
        }
    }

    static class EventComparator implements Comparator<Event> {
        @Override
        public int compare(Event event1, Event event2) {
            if (event1.time() == event2.time()) {
                if (event2 instanceof PersonObserverEvent && event1 instanceof final FlowerEvent flowerEvent) {
                    return flowerEvent.diff * -1;
                } else if (event1 instanceof PersonObserverEvent && event2 instanceof final FlowerEvent flowerEvent) {
                    return flowerEvent.diff;
                }
            }

            return Integer.compare(event1.time(), event2.time());
        }
    }
}
