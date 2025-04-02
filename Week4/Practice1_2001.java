//온라인 채점 사이트에 제출한 코드에서 주석을 추가한 코드입니다! 온라인에 제출한 코드와 fundemental한 내용은 동일합니다!! <23101150_강민찬>
//깃허브에서 직접 좌측 실행버튼을 눌러 컴파일 후 테스트케이스들을 입력하여 태스트해볼수도 있습니다!
package Week4;

import java.util.*; // java.util 패키지에 포함된 Scanner, ArrayList, HashMap, Stack 등의 클래스를 사용합니다.

public class Practice1_2001 {
    // Index 클래스는 배열에서 현재 읽고 있는 위치를 저장하기 위해 사용합니다.
    static class Index {
        int pos = 0; // 현재 토큰 배열에서 읽고 있는 위치를 나타내는 변수입니다.
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 첫 번째 입력: 명제 변수의 개수 (예: 1, 2, 또는 3)
        int numVariables = sc.nextInt();
        // 두 번째 입력: 논리 연산자의 개수 (문제에서는 최대 4개까지 사용한다고 가정)
        int numOperators = sc.nextInt();
        // 세 번째 입력: 논리 연산식에 포함된 명제 변수와 연산자의 총 개수 (토큰의 개수)
        int tokenCount = sc.nextInt();

        // 네 번째 입력: 실제 논리 연산식의 토큰들이 공백으로 구분되어 입력됩니다.
        // 예시: "P o Q a n R i P" 와 같이 입력된 경우, 각각의 토큰을 배열에 저장합니다.
        String[] tokens = new String[tokenCount];
        for (int i = 0; i < tokenCount; i++) {
            tokens[i] = sc.next();
        }

        // 출력할 때 명제 변수들을 항상 "P Q R" 순서대로 출력합니다.
        // 단, 입력받은 명제 변수의 개수에 따라 출력할 변수의 수가 달라집니다.
        if (numVariables == 1) {
            System.out.println("P RESULT");
            System.out.println(); // 빈 줄을 추가하지 않으면 틀린 판정이 나오므로 꼭 추가합니다.
        } else if (numVariables == 2) {
            System.out.println("P Q RESULT");
            System.out.println();
        } else if (numVariables == 3) {
            System.out.println("P Q R RESULT");
            System.out.println();
        }

        // 가능한 모든 진리값 조합에 대해 논리식의 결과를 평가하고 출력합니다.
        // 각 경우마다 명제 변수(P, Q, R)의 값과 평가 결과를 "T" (True) 또는 "F" (False)로 출력합니다.
        if (numVariables == 1) {
            // 변수 1개인 경우: P만 사용됩니다.
            for (boolean p : new boolean[]{true, false}) {
                // 각 조합에 대해 변수의 값을 저장하는 맵 생성
                Map<Character, Boolean> values = new HashMap<>();
                values.put('P', p);
                // 논리식을 평가하여 결과를 boolean 값으로 반환
                boolean result = evaluateExpression(tokens, values);
                // 결과를 출력: 예를 들어 "T T" 또는 "F F"와 같이 출력됩니다.
                System.out.println((p ? "T" : "F") + " " + (result ? "T" : "F"));
                System.out.println(); // 빈 줄을 추가 하지 않으면 틀린 판정이 나오므로 꼭 추가
            }
        } else if (numVariables == 2) {
            // 변수 2개인 경우: P와 Q 사용
            for (boolean p : new boolean[]{true, false}) {
                for (boolean q : new boolean[]{true, false}) {
                    Map<Character, Boolean> values = new HashMap<>();
                    values.put('P', p);
                    values.put('Q', q);
                    boolean result = evaluateExpression(tokens, values);
                    System.out.println((p ? "T" : "F") + " " + (q ? "T" : "F") + " " + (result ? "T" : "F"));
                    System.out.println();
                }
            }
        } else if (numVariables == 3) {
            // 변수 3개인 경우: P, Q, R 모두 사용
            for (boolean p : new boolean[]{true, false}) {
                for (boolean q : new boolean[]{true, false}) {
                    for (boolean r : new boolean[]{true, false}) {
                        Map<Character, Boolean> values = new HashMap<>();
                        values.put('P', p);
                        values.put('Q', q);
                        values.put('R', r);
                        boolean result = evaluateExpression(tokens, values);
                        System.out.println((p ? "T" : "F") + " " + (q ? "T" : "F") + " " + (r ? "T" : "F") + " " + (result ? "T" : "F"));
                        System.out.println();
                    }
                }
            }
        }
        sc.close();
    }

    /**
     * evaluateExpression 메서드는 논리식의 결과값을 알아봅니다.
     * 이때, 연산자 우선순위는 NOT(n)이 가장 높고, 나머지 연산자(AND: a, OR: o, IMPLIES: i)는 모두 동일한 우선순위를 가지며 왼쪽 결합법칙을 따릅니다.
     *
     * @param tokens : 논리식이 저장된 문자열 배열
     * @param values : 명제 변수의 값을 저장한 Map
     * @return    :    논리식의 최종 평가 결과
     */
    private static boolean evaluateExpression(String[] tokens, Map<Character, Boolean> values) {
        // Index 객체를 생성하여 토큰 배열에서 현재 읽고 있는 위치를 관리합니다.
        Index index = new Index();
        // 먼저 첫 번째 피연산자를 읽어옵니다.
        // 피연산자 앞에 'n' (NOT 연산자)가 연속해서 있을 수 있으므로 readOperand 메서드를 사용합니다.
        boolean left = readOperand(tokens, index, values);

        // 이후 남은 토큰들을 순차적으로 읽으며, 이항 연산자와 피연산자가 번갈아 나ㅌ난다고 가정합니다.
        while (index.pos < tokens.length) {
            // 현재 인덱스의 토큰은 이항 연산자여야 합니다.
            String op = tokens[index.pos++];  // 연산자 토큰을 읽고 인덱스를 1 증가시킵니다.
            // 이항 연산자의 오른쪽 피연산자를 읽습니다.
            boolean right = readOperand(tokens, index, values);
            // 왼쪽 값과 연산자, 오른쪽 값을 이용하여 결과를 계산하고, 이를 새로운 왼쪽 값으로 갱신합니다.
            left = applyOperator(left, op, right);
        }
        // 모든 토큰을 처리한 후 최종 결과를 반환합니다.
        return left;
    }

    /**
     * readOperand 메서드는 토큰 배열에서 피연산자(명제 변수)의 값을 읽어오는 역할을 합니다.
     * 피연산자 앞에 NOT(n) 연산자가 연속해서 올 수 있으므로, 이를 모두 처리한 후 실제 명제 변수의 값을 읽습니다.
     *
     * @param tokens  논리식의 토큰 배열
     * @param index   현재 읽고 있는 위치를 관리하는 Index 객체
     * @param values  명제 변수의 실제 값을 저장한 Map
     * @return        피연산자에 대해 NOT 연산자를 적용한 최종 boolean 값
     */
    private static boolean readOperand(String[] tokens, Index index, Map<Character, Boolean> values) {
        int notCount = 0; // NOT 연산자 'n'의 개수를 저장할 변수입니다.

        // 현재 인덱스에서 NOT 연산자 'n'이 연속되는 동안 반복합니다.
        while (index.pos < tokens.length && tokens[index.pos].equals("n")) {
            notCount++;    // NOT 연산자의 개수를 증가시킵니다.
            index.pos++;   // 인덱스를 1 증가시켜 다음 토큰으로 이동합니다.
        }

        // NOT 연산자 처리 후, 다음 토큰은 명제 변수 (예: P, Q, R)여야 합니다.
        String token = tokens[index.pos++];
        // 변수의 실제 값(true 또는 false)을 Map에서 가져옵니다.
        boolean val = values.get(token.charAt(0));
        // 만약 NOT 연산자가 홀수번 나오면 피연산자의 값을 반전시켜 반환합니다.
        // 짝수번이면 원래 값 그대로 반환합니다.
        return (notCount % 2 == 1) ? !val : val;
    }

    /**
     * applyOperator 메서드는 이항 연산자를 적용하여 두 boolean 값을 결합합니다.
     *
     * @param left   왼쪽 피연산자의 boolean 값
     * @param op     적용할 연산자 ("a"는 AND, "o"는 OR, "i"는 IMPLIES)
     * @param right  오른쪽 피연산자의 boolean 값
     * @return       연산 적용 후의 boolean 결과
     */
    private static boolean applyOperator(boolean left, String op, boolean right) {
        // switch 문을 사용하여 각 연산자에 따라 적절한 논리 연산을 수행합니다.
        switch (op) {
            case "a":
                // AND 연산: 두 값이 모두 true여야 true입니다.
                return left && right;
            case "o":
                // OR 연산: 두 값 중 하나라도 true이면 true입니다.
                return left || right;
            case "i":
                // IMPLIES 연산: (left → right)는 (!left) OR right와 동일합니다.
                return (!left) || right;
            default:
                // 만약 알 수 없는 연산자가 들어오면 예외를 발생시킵니다. 문제에선 여기까진 요구하진 않았으나 예외처리를 위해 추가했습니다.
                throw new IllegalArgumentException("알 수 없는 연산자: " + op);
        }
    }
}
