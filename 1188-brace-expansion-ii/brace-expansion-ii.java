import java.util.*;

class Solution {

    private String s;
    private int index;

    public List<String> braceExpansionII(String expression) {
        s = expression;
        index = 0;

        Set<String> result = parseExpression();

        List<String> answer = new ArrayList<>(result);
        Collections.sort(answer);

        return answer;
    }

    // Handles UNION: a,b,c
    private Set<String> parseExpression() {
        Set<String> result = parseConcatenation();

        while (index < s.length() && s.charAt(index) == ',') {
            index++; // skip comma
            result.addAll(parseConcatenation());
        }

        return result;
    }

    // Handles CONCATENATION: ab{c,d}ef
    private Set<String> parseConcatenation() {
        Set<String> result = new HashSet<>();
        result.add("");

        while (index < s.length()
                && s.charAt(index) != ','
                && s.charAt(index) != '}') {

            Set<String> part = new HashSet<>();

            if (s.charAt(index) == '{') {
                index++; // skip {

                part = parseExpression();

                index++; // skip }
            } 
            else {
                // Single lowercase letter
                part.add(String.valueOf(s.charAt(index)));
                index++;
            }

            // Cartesian product for concatenation
            Set<String> next = new HashSet<>();

            for (String a : result) {
                for (String b : part) {
                    next.add(a + b);
                }
            }

            result = next;
        }

        return result;
    }
}