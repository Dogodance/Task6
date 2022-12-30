import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class Main {
    public static void main(String[] args) {
// ������� �1
        System.out.println(bell(3));

// ������� �2
        System.out.println(translateWord("Apple"));
        System.out.println(translateWord("flag"));
        System.out.println(translateSentence("I like to eat honey waffles."));

// ������� �3
        System.out.println(validColor("rgb(255,256,255)"));

// ������� �4
        System.out.println(stripUrlParams("https://edabit.com?a=1&b=2&a=2", new String[]{}));

// ������� �5
        System.out.println( Arrays.toString(getHashTags("Why You Will Probably Pay More for Your Christmas Tree This Year")));

// ������� �6
        System.out.println(ulam(206));

// ������� �7
        System.out.println(longestNonrepeatingSubstring("abcabcbb"));

// ������� �8
        System.out.println(convertToRoman(3999));

// ������� �9
        System.out.println( formula("16 * 10 = 160 = 14 + 120"));

// ������� �10
        System.out.println(palindromeDescendant(11211230));
    }

    // ������� �1. ����� �����
    public static int bell(int n) {
        int[] row = new int[n]; // ������� ������ ������ ������� ������ �����
        int previous;  // ���������� ��� ���������� ��������
        int temp; // ��������� ����������
        row[0] = 1; // ������� ������ ������� ������� 1
        for (int i = 1; i < n; i++) { // ������� ������ �� �����
            previous = row[0]; // ����� ������������� � prev - ������ ������� �������
            row[0] = row[i - 1]; // ����� �������� ������ ������� ������� �� ���������� �� �������� �������� ������
            for (int j = 1; j <= i; j++) { // ������� �� 2 �������� ������� �� i-�� ������������
                temp = row[j]; // ���������� ����� ������� � ������� ���������� temp
                row[j] = row[j - 1] + previous; // ���������� ��� j-�� ������ ������ ���������� �� j-�� � ���������� prev
                previous = temp; // prev ������ ����������� row[j]
            }
        }
        return row[n - 1];
    }

    // ������� �2. ��������� ������
    public static String translateWord(String word) {
        if (word.length() == 0) {// ���� ����� ����� 0, �� ������ �������
            return "";
        }

        char firstChar = word.charAt(0); // ������� ������ ������
        if (isVowel(firstChar, false)) { // ���� ������ ������ �������, ��� ���� y �� ������� ��������
            return word + "yay"; // ������ ����� + ��������� yay
        }

        int index = 1; // ������� ��������� ������
        for (index = 1; index < word.length(); ++index) // ������� ��� �����
            if (isVowel(word.charAt(index), true)) { // ���� ����� �������, ��� ���� y ������ ������� ��������
                break; // ������������� ����
            }

        if (Character.isUpperCase(firstChar)) { // ���� ������ ������ ������� � ������� ��������
            if (word.length() == index) {// � ��� ���� ������� �� ������� ���������, �������� ���������
                return word + "ay"; // ��������� � ����� ��������� ay
            }
            // ���� ����� ���������� � ����������, ����������� ������ ����� (�����) ����� ��
            //�������� �� ����� ����� � �������� �ay� � �����
            return Character.toUpperCase(word.charAt(index)) + word.substring(index + 1) // ���� ������ ������� �� �����������
                    + Character.toLowerCase(firstChar) + word.substring(1, index)
                    + "ay";
        }

        return word.substring(index) + word.substring(0, index) + "ay";
    }

    // �������� ����������
    public static String translateSentence(String sentence) {
        StringBuilder sentenceBuilder = new StringBuilder(); // ��������� StringBuilder, ��� �� ������������ ����� ������������� ��������� ������
        int start = 0; // ������ ������
        int index = 0;

        for (index = 0; index < sentence.length(); index++) { // ������� ������ ���������� ����������
            char c = sentence.charAt(index); // ������� ������ �� �������
            if (!Character.isLetter(c)) { // ���� ������ �� �����
                sentenceBuilder.append(translateWord(sentence.substring(start, index))); //����� ������� ����� �� ���� ����� � ��������� ���
                sentenceBuilder.append(c); // ������� ������ � ������������������
                start = index + 1; // ������� start �� ������� �� ����� + 1
            }
        }
        if (index - start > 0) {  // ���� ����� ������ �������� �� ������������ �����
            sentenceBuilder.append(translateWord(sentence.substring(start, index))); // ��������� ��� � ������� � ������������������
        }
        return sentenceBuilder.toString();
    }

    // ���������������� ����� �������� ��������
    private static boolean isVowel(char c, boolean isYVowel) {
        switch (c) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
            case 'A':
            case 'E':
            case 'I':
            case 'O':
            case 'U':
                return true;
            case 'y':
            case 'Y':
                return isYVowel;
            default:
                return false;
        }
    }

    // ������� �3. ��������� RGB(A)
    public static boolean validColor(String color) {
        String s = color.substring(color.indexOf("(") + 1, color.indexOf(")")); // ����� ��������� ����� ����� �������� ��������
        String[] parts = s.split(","); // ����� �� ������ �����, ��� ������������ �������� �������

        // ���� ������ ����� ����� ����������� ������� �������, ��������� �������
        if (color.contains(" ("))
            return false;

        // �������� ��������� �� ������� ���������� ����� �����
        if (color.contains("rgba") && parts.length != 4) // ���� �������� rgba, � ������ parts �� ������� 4, �� ��������
            return false;
        else if (color.contains("rgb(") && parts.length != 3) // ���� �������� rgb, � ������ part ������� 3, �� ��������(������ � ������ ����� ����������, ����� �� ������� � rgba)
            return false;
        int num = 0;
        double dec = 0;
        for (String value : parts) { // ������� �������� ������
            if (value.equals("")) // ���� �������� ������, ������ ������ �� �����
                return false;
            if (value.contains(".")) { // ���� �������� �����, ������ ���� ������� ��� ��� ������ Double
                dec = Double.parseDouble(value);
            } else {
                num = Integer.parseInt(value);// ����� ���� �� �������� �����, ���� ������� ��� Int
            }
            // ���� ��� �� rgba � ��� ���� �������� �������� �����, ������ �� ��������
            if (!color.contains("rgba") && value.contains("."))
                return false;
            // ���� �������� �� �� 0 �� 255, ������ �� ��������
            if (num < 0 || num > 255)
                return false;
            // ���� rgba � �������� �����, ��� ���� �������� � ��������� ������� ������ 1 ��� ������ 0, ������ �� ��������
            if (color.contains("rgba") &&
                    (value.contains(".") && dec > 1.0 ||
                            (value.contains(".") && dec < 0)))
                return false;
        } // ���� �� ���� �� ���������, ������ ��������
        return true;
    }

    // ������� �4. �������� ����������
    public static String stripUrlParams(String url, String[] paramsToStrip) {

        if (!url.contains("?")) // ���������, �������� �� ����� �������������� ����
            return url;

        String[] parts = url.split("\\?"); // �������������� ������ � ������ "parts", ������� ����� ��������� ��� �������� � ���� �����.
        // ������ ������� - ��� ������ �������� ��������� URL-������, ������ �� ��������������� �����.
        // � ������ ������� - ��� ������ �������� ��������� URL-������ ����� ��������������� �����
        StringBuilder sb = new StringBuilder(parts[0]); // ������ ��������� SB, ���������� ������ �������� ������
        String[] params = parts[1].split("&"); // ������, ���������� ������ ��������, ������������ �� ������ �������� ��������� URL-������, ������� ����������� �����������
        HashMap<String, String> map = new HashMap<>(); // ������ ���-�����, ���������� ������ ��� �����, ��� � ��������

        for (String param : params) { // ������� ���������
            String[] parm = param.split("="); // ���������� ����������� ���� =
            map.put(parm[0], parm[1]); // ������ � ������� ���� �������� ����� ������ �����, � � value �������� ����� ������ �����
        }

        LinkedHashSet<String> strip = new LinkedHashSet<>();

        if(paramsToStrip != null) { // ���� ������ ����� ������������ ���������� �� ������
            strip.addAll(Arrays.asList(paramsToStrip)); // �� ������� ��� ��������� � ��������� strip
        }

        sb.append("?"); // ������� � ����� ������ ������
        int k = 1; // ������� �����������

        for(String key : map.keySet()) { // ������� ����� ������������� �������
            if(!strip.contains(key)) // ���� ���� ����� ���������� � ������� ����������
            {
                if(k > 1) // ���� k > 1
                    sb.append("&"); // ������� ������ ���������
                sb.append(key).append("=").append(map.get(key)); // ������� ��� ���������, ���� ����� � ����� ��������
                k++;// �������� ������� ���������� �� 1
            }
        }

        return sb.toString();
    }

    // ������� �5. �������
    public static String[] getHashTags(String str) {
        str = str.replaceAll(",", ""); // �������� ���� �������
        String[] strArr = str.split(" "); // �������� �����, � �������� � ������ strArr
        int maxNum = 0, pos = 0; // pos - �������  maxNum - ������������ �����
        String longestWord = "", hashes = "";  // longestWord - ����� ������� �����

        if(strArr.length < 3){ // ���� ���������� ����� ������ 3
            for(String word:strArr){ // ������� �����
                hashes += "#" + word.toLowerCase() + ","; // ������ = - ��� ����� � ������ �������� + ������� � ������
            }
        }else{
            for(int i = 0; i < 3; i++){ // ���� ��� 3 ����� ������� �����
                for(int j = 0; j < strArr.length; j++){ // ������� ��� �����
                    if (strArr[j].length() != 0) { // ���� ����� ������ �� ��������� 0
                        if(strArr[j].length() >= maxNum){ // ���� ����� ������ ������ ��� ��������� ��������� ����������� ������������ �����
                            maxNum = strArr[j].length(); // ����������� ������������ ������� �� ����� ������ �� ���������� �����
                            longestWord = strArr[j].toLowerCase(); // ����������� ������� �����
                            pos = j; // �������� �������
                        }
                    }
                }
                strArr[pos] = " "; // ������� ���������� ����� �� �������
                hashes += "#" + longestWord + ","; // ������� � ����
                maxNum = 0; // ������� ������� ��� ���������� ��������

            }
        }
        String[] res = hashes.split(","); // ������ �������� �� ������ �������� ��������� ��� ����������� �������

        return res;
    }

    // ������� �6. ������������������ �����
    public static int ulam(int n) {
        ArrayList<Integer> arr = new ArrayList<>(); // �������� ������
        arr.add(1); // ������� 1
        arr.add(2); // ������� 2
        int i, j = 2; // ���������� ��� ������
        for (i = 3; j < n; i++) { // ������ ����, ������ i, �� �������� j
            int count = 0; // �������
            for (int k = 0; k < arr.size() - 1; k++) { // ������� ������
                for (int l = k + 1; l < arr.size(); l++) { // ������� ������ ������ ������ �� ���������� ��������
                    if (arr.get(k) + arr.get(l) == i) // ���� ����� ��������� ����� i
                        count++; // ������� �������� �� 1
                    if (count > 1) // ���� ������� ������ 1 (2) ����� ��������� ����
                        break;
                }
                if (count > 1) // ���� ������� ������ 1 (2) ����� ��������� ����
                    break;
            }
            if (count == 1) { // ���� ������� ����� 1, ����� ������� � ������ ���� ������� � �������� j
                arr.add(i);
                j++;
            }
        }
        return i - 1;
    }

    // ������� �7. ������� ��������������� ������
    public static String longestNonrepeatingSubstring(String str) {
        String res = "", temp = ""; // res - ��������� temp - ��������� ������
        for(int i = 0; i < str.length(); i++){ // ������� ������, ���������� ����������
            for(int j = i; j < str.length(); j++){ // ������� �� i �� ����� ������ ������ ������
                if(!temp.contains(String.valueOf(str.charAt(j)))) // ���� ��������� ������ �� �������� ������ ������ �� j
                    temp += str.charAt(j); // ������� ��� � ��������� ������
                else{ // ����� �������� ����� ��������� ������ � ��������������. ���� ������
                    if(temp.length() > res.length())
                        res = temp; // ����������� �������������� ������ ��������� �������
                    temp = ""; // ������� ��������� ������
                    j = str.length(); // j ������� � ����� ������
                }
            }
        }
        return res;
    }

    // ������� �8. ������� �����
    public static String convertToRoman(int num) {
        String fs = ""; // ������ ���������
        while(num != 0){ // ���� ����� �� ����������(�� ���� �� ����� 0)
            if(num >= 1000){ // ���� ����� ������ 1000
                num -= 1000; // ������� 1000
                fs += "M"; // ������� ����� M
            }
            else if(num >= 900){ // ���� ����� ������ 900, ������ 1000
                num -= 900; // ������� 900
                fs += "CM"; // ������� CM
            }
            else if(num >= 500){ // ���� ����� ������ 500
                num -= 500; // ������� 500
                fs += "D"; // ������� D
            }
            else if(num >= 400){ // ���� ����� ������ 400
                num -= 400; // ������� 400
                fs += "CD"; // ������� CD
            }
            else if(num >= 100){ // ���� ����� ������ 100
                num -= 100; // ������� 100
                fs += "C"; // ������� C
            }
            else if(num >= 90){ // ���� ����� ������ 90
                num -= 90; // ������� 90
                fs += "XC"; // ������� XC
            }
            else if(num >= 50){ // ���� ����� ������ 50
                num -= 50; // ������� 50
                fs += "L"; // ������� L
            }
            else if(num >= 40){ // ���� ����� ������ 40
                num -= 40; // ������� 40
                fs += "XL"; // ������� XL
            }
            else if(num >= 10){ // ���� ����� ������ 10
                num -= 10; // ������� 10
                fs += "X"; // ������� X
            }
            else if(num >= 9){ // ���� ����� ������ 9
                num -= 9; // ������� 9
                fs += "IX"; // ������� IX
            }
            else if(num >= 5){ // ���� ����� ������ 5
                num -= 5; // ������� 5
                fs += "V"; // ������� V
            }
            else if(num >= 4){ // ���� ����� ������ 4
                num -= 4; // ������� 4
                fs += "IV"; // ������� IV
            }
            else if(num > 0){ // ���� ����� ������ 1
                num -= 1;// ������� 1
                fs += "I"; // ������� I
            }
        }
        return fs;
    }

    // ������� �9. ����� �� �������?
    public static int calc(String str) {
        str = str.replaceAll("[()]", ""); // ������� ������� ������
        str = str.replaceAll(" ", ""); // ������� �������
        try {
            return Integer.parseInt(str); // �������� �������� ������ � �����
        } catch (NumberFormatException e) { // ���� �������� � ������� �����
            String[] tokens; // ��������� ������ ����� �������
            tokens = str.split("\\*", 2);  // �������� ������� �� ���� ������������
            if (tokens.length > 1) { // ���� ������ 2, �� ���������� ���������
                return calc(tokens[0]) * calc(tokens[1]);
            }
            tokens = str.split("/", 2); // ���� �� �����, �� �������� ������� �� �������� �������
            if (tokens.length > 1) { // ���� ������ 2, �� ���������� �������
                return calc(tokens[0]) / calc(tokens[1]);
            }
            tokens = str.split("\\+", 2); // ���� �� �����, �� �������� ������� �� �������� ��������
            if (tokens.length > 1) { // ���� ������ 2, �� ���������� �������
                return calc(tokens[0]) + calc(tokens[1]);
            }
            tokens = str.split("-", 2); // ���� �� �����, �� �������� ������� �� �������� ���������
            if (tokens.length > 1) {// ���� ������ 2, �� ���������� �������
                return calc(tokens[0]) - calc(tokens[1]);
            }
            return 0; // ���� ����� �� �����, ������ 0
        }
    }
    public static boolean formula(String str) {
        // ����� ������ �� ������, ��� ������������ �������� ���� =
        String[] equations = str.split("=");
        for (int i = 0; i < equations.length-1; i++)// ������� ������ ����� ������ "���������"
        {
            if(calc(equations[i]) != calc(equations[i+1])) { // ���� ��������� �������� �� ������� �������� � ���������� �������� �� �����
                return false; // �� ���������� false
            }
        }
        // ���� �� ����������� ���������� true
        return true;

    }

    // ������� �10. ���������
    public static boolean palindromeDescendant(int num) {
        boolean isSym = false; // ���� ��������������
        while(num > 9) { // ���� ����� �� ����� �������������
            if(isSymmetrical(num)) { // ��������� �� �������������
                isSym = true; // �������, ��� ��� �����������
                break; // ������������� ��������
            }
            num = getSumofDigits(num); // ����� ���������� �������
        }
        return isSym;
    }
    public static boolean isSymmetrical(int num) {
        int reversenum = 0, n = num; // reversenum - �������� ����� n - � ���������� ����
        if(n < 0) { // ���� ����� �������������
            n = n * -1; // �������� ��� �� -1, �� ���� ������ �������������
        }
        while(n != 0) {  // ���� ����� �� ������ 0
            reversenum = reversenum * 10; // �������� �������� ����� �� 10
            reversenum = reversenum + n % 10; // ���������� � ��������� ����� ������ ����� ������ �����
            n = n / 10; // ������� ����� ������ �����
        }
        return(reversenum == num); // ���� �������� ����� � ���� ����� �����, ������ ����������
    }
    public static int getSumofDigits(int n) {
        String iString = Integer.toString(n); // �������� ����� � ������
        StringBuilder sb = new StringBuilder(); // ��������� StringBuilder

        for(int i = 0; i < iString.length() - 1; i += 2) { // ������� ����� �� ��������
            int i1 = Integer.parseInt(Character.toString(iString.charAt(i))); // �������� index ��������
            int i2 = Integer.parseInt(Character.toString(iString.charAt(i+1))); // �������� index �����������
            int num = i1 + i2; // ���������� �������
            sb.append(num); // ������� � ������ ����� ��������
        }

        return Integer.parseInt(sb.toString()); // ����������� ����� ������ � ������
    }
}