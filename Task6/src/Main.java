import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class Main {
    public static void main(String[] args) {
// Задание №1
        System.out.println(bell(3));

// Задание №2
        System.out.println(translateWord("Apple"));
        System.out.println(translateWord("flag"));
        System.out.println(translateSentence("I like to eat honey waffles."));

// Задание №3
        System.out.println(validColor("rgb(255,256,255)"));

// Задание №4
        System.out.println(stripUrlParams("https://edabit.com?a=1&b=2&a=2", new String[]{}));

// Задание №5
        System.out.println( Arrays.toString(getHashTags("Why You Will Probably Pay More for Your Christmas Tree This Year")));

// Задание №6
        System.out.println(ulam(206));

// Задание №7
        System.out.println(longestNonrepeatingSubstring("abcabcbb"));

// Задание №8
        System.out.println(convertToRoman(3999));

// Задание №9
        System.out.println( formula("16 * 10 = 160 = 14 + 120"));

// Задание №10
        System.out.println(palindromeDescendant(11211230));
    }

    // Задание №1. Число Белла
    public static int bell(int n) {
        int[] row = new int[n]; // Объявим массив строку длинной самого числа
        int previous;  // Переменная под предыдущее значение
        int temp; // Временная переменная
        row[0] = 1; // Объявим первый элемент массива 1
        for (int i = 1; i < n; i++) { // Обойдем массив до конца
            previous = row[0]; // Будем устанавливать в prev - первый элемент массива
            row[0] = row[i - 1]; // Будем заменять первый элемент массива на предыдущий от текущего элемента обхода
            for (int j = 1; j <= i; j++) { // Обойдем от 2 элемента массива до i-го включительно
                temp = row[j]; // Произведем обмен данными с помощью переменной temp
                row[j] = row[j - 1] + previous; // Переменная под j-ым станет суммой предыдущей от j-ой и переменной prev
                previous = temp; // prev станет запомненным row[j]
            }
        }
        return row[n - 1];
    }

    // Задание №2. Поросячья латынь
    public static String translateWord(String word) {
        if (word.length() == 0) {// Если длина слова 0, то вернем пустоту
            return "";
        }

        char firstChar = word.charAt(0); // Возьмем первый символ
        if (isVowel(firstChar, false)) { // Если первый символ гласный, при этом y не считаем глассной
            return word + "yay"; // вернем слОво + окончание yay
        }

        int index = 1; // Возьмем следующий индекс
        for (index = 1; index < word.length(); ++index) // Обойдем все слово
            if (isVowel(word.charAt(index), true)) { // если буква гласная, при этом y теперь считаем глассной
                break; // Останавливаем цикл
            }

        if (Character.isUpperCase(firstChar)) { // Если первый символ написан в верхнем регистре
            if (word.length() == index) {// И при этом гласная на которой закончили, является последней
                return word + "ay"; // добавляем к слову окончание ay
            }
            // Если слово начинается с согласного, переместите первую букву (буквы) слова до
            //гласного до конца слова и добавьте «ay» в конец
            return Character.toUpperCase(word.charAt(index)) + word.substring(index + 1) // Если другие условия не срабатывали
                    + Character.toLowerCase(firstChar) + word.substring(1, index)
                    + "ay";
        }

        return word.substring(index) + word.substring(0, index) + "ay";
    }

    // Обратный переводчик
    public static String translateSentence(String sentence) {
        StringBuilder sentenceBuilder = new StringBuilder(); // Определим StringBuilder, что бы пользоваться всеми возможностями изменения строки
        int start = 0; // Откуда начали
        int index = 0;

        for (index = 0; index < sentence.length(); index++) { // Обходим строку переданную параметром
            char c = sentence.charAt(index); // Считаем символ по индексу
            if (!Character.isLetter(c)) { // если символ не буква
                sentenceBuilder.append(translateWord(sentence.substring(start, index))); //Тогда считаем слово до этой буквы и переведем его
                sentenceBuilder.append(c); // Добавим строку к последовательности
                start = index + 1; // Сдвинем start на позицию не буквы + 1
            }
        }
        if (index - start > 0) {  // Если после обхода осталось не обработанное слово
            sentenceBuilder.append(translateWord(sentence.substring(start, index))); // Переведем его и добавим в последовательность
        }
        return sentenceBuilder.toString();
    }

    // Воспомогательный метод проверки глассных
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

    // Задание №3. Параметры RGB(A)
    public static boolean validColor(String color) {
        String s = color.substring(color.indexOf("(") + 1, color.indexOf(")")); // Берем подстроку между двумя круглыми скобками
        String[] parts = s.split(","); // делим на массив строк, где разделителем является запятая

        // Если пробел стоим перед открывающей круглой скобкой, выражение неверно
        if (color.contains(" ("))
            return false;

        // Проверка Правильно ли указано количество чисел цвета
        if (color.contains("rgba") && parts.length != 4) // Если название rgba, а массив parts не длинной 4, не подходит
            return false;
        else if (color.contains("rgb(") && parts.length != 3) // Если название rgb, а массив part длинной 3, не подходит(скобку в поиске пишем специально, чтобы не спутать с rgba)
            return false;
        int num = 0;
        double dec = 0;
        for (String value : parts) { // Обходим значения цветов
            if (value.equals("")) // Если значение пустое, значит задано не верно
                return false;
            if (value.contains(".")) { // Если содержит точку, значит надо парсить как тип данных Double
                dec = Double.parseDouble(value);
            } else {
                num = Integer.parseInt(value);// Иначе если не содержит точку, надо парсить как Int
            }
            // Если тип не rgba и при этом значение содержит точку, значит не подходит
            if (!color.contains("rgba") && value.contains("."))
                return false;
            // Если диапозон не от 0 до 255, значит не подходит
            if (num < 0 || num > 255)
                return false;
            // Если rgba и содержит точку, при этом значение с плавающей запятой больше 1 или меньше 0, значит не подходит
            if (color.contains("rgba") &&
                    (value.contains(".") && dec > 1.0 ||
                            (value.contains(".") && dec < 0)))
                return false;
        } // Если ни разу не подходило, значит подходит
        return true;
    }

    // Задание №4. Удаление параметров
    public static String stripUrlParams(String url, String[] paramsToStrip) {

        if (!url.contains("?")) // Проверяем, содержит ли адрес вопросительный знак
            return url;

        String[] parts = url.split("\\?"); // инициализируем массив с именем "parts", который будет содержать два элемента в виде строк.
        // Первый элемент - это первая половина входящего URL-адреса, вплоть до вопросительного знака.
        // И второй элемент - это вторая половина входящего URL-адреса после вопросительного знака
        StringBuilder sb = new StringBuilder(parts[0]); // Создаём экземпляр SB, содержащий первую половину адреса
        String[] params = parts[1].split("&"); // Массив, содержащий каждый параметр, содержащийся во второй половине входящего URL-адреса, который разбивается амперсандом
        HashMap<String, String> map = new HashMap<>(); // Создаём хэш-карту, содержащую строки как ключи, так и значения

        for (String param : params) { // Обходим параметры
            String[] parm = param.split("="); // Испольузем разделитель знак =
            map.put(parm[0], parm[1]); // Кладем в словарь ключ значение перед знаком равно, а в value значение после знакак равно
        }

        LinkedHashSet<String> strip = new LinkedHashSet<>();

        if(paramsToStrip != null) { // Если массив строк передаваемых параметров не пустой
            strip.addAll(Arrays.asList(paramsToStrip)); // то добавим все параметры в множество strip
        }

        sb.append("?"); // Добавим в конец строки вопрос
        int k = 1; // Счетчик параметролв

        for(String key : map.keySet()) { // Обойдем ключи получившегося словаря
            if(!strip.contains(key)) // Если ключ также содержится в словаре параметров
            {
                if(k > 1) // Если k > 1
                    sb.append("&"); // Добавим строке амперсанд
                sb.append(key).append("=").append(map.get(key)); // добавим имя параметра, знак равно и потом значение
                k++;// Увеличим счетчик параметров на 1
            }
        }

        return sb.toString();
    }

    // Задание №5. Хештэги
    public static String[] getHashTags(String str) {
        str = str.replaceAll(",", ""); // Удаление всех запятых
        String[] strArr = str.split(" "); // Разделим слова, и поместим в массив strArr
        int maxNum = 0, pos = 0; // pos - позиция  maxNum - максимальный номер
        String longestWord = "", hashes = "";  // longestWord - самое длинное слово

        if(strArr.length < 3){ // Если количество слово меньше 3
            for(String word:strArr){ // Обходим слова
                hashes += "#" + word.toLowerCase() + ","; // хештег = - это слово в нижнем регистре + решетка в начале
            }
        }else{
            for(int i = 0; i < 3; i++){ // цикл для 3 самых длинных слово
                for(int j = 0; j < strArr.length; j++){ // Обходим все слова
                    if (strArr[j].length() != 0) { // Если длина строка не равняется 0
                        if(strArr[j].length() >= maxNum){ // Если длина строки больше или равняется последней запомненной максимальной длине
                            maxNum = strArr[j].length(); // Перезапищем максимальную длинную на новую взятую из сверяемого слова
                            longestWord = strArr[j].toLowerCase(); // Перезапищем длинное слово
                            pos = j; // Запомним позицию
                        }
                    }
                }
                strArr[pos] = " "; // Заменим наибольшее слово на пустоту
                hashes += "#" + longestWord + ","; // Добавим к хешу
                maxNum = 0; // Обнулим счетчик для повторного подсчета

            }
        }
        String[] res = hashes.split(","); // Строку разобъем на массив хештегов используя как разделитель запятую

        return res;
    }

    // Задание №6. Последовательность Улама
    public static int ulam(int n) {
        ArrayList<Integer> arr = new ArrayList<>(); // Создадим список
        arr.add(1); // Добавим 1
        arr.add(2); // Добавим 2
        int i, j = 2; // Переменные для циклов
        for (i = 3; j < n; i++) { // Начнем цикл, двигая i, но проверяя j
            int count = 0; // Счетчик
            for (int k = 0; k < arr.size() - 1; k++) { // Обходим список
                for (int l = k + 1; l < arr.size(); l++) { // Обходим вторым циклом список со следующего элемента
                    if (arr.get(k) + arr.get(l) == i) // Если сумма элементов равна i
                        count++; // Счетчик увеличим на 1
                    if (count > 1) // Если счетчик больше 1 (2) тогда остановим цикл
                        break;
                }
                if (count > 1) // Если счетчик больше 1 (2) тогда остановим цикл
                    break;
            }
            if (count == 1) { // Если счетчик равен 1, тогда добавим в список этот элемент и увеличим j
                arr.add(i);
                j++;
            }
        }
        return i - 1;
    }

    // Задание №7. Длинная неповторяющаяся строка
    public static String longestNonrepeatingSubstring(String str) {
        String res = "", temp = ""; // res - результат temp - временная строка
        for(int i = 0; i < str.length(); i++){ // обходим строку, переданную параметром
            for(int j = i; j < str.length(); j++){ // Обходим от i до конца строки вторым циклом
                if(!temp.contains(String.valueOf(str.charAt(j)))) // Если временная строка не содержит символ взятый по j
                    temp += str.charAt(j); // Добавим его к временной строке
                else{ // Иначе проверим длину временной строки с результирующей. Если больше
                    if(temp.length() > res.length())
                        res = temp; // Перезапишем результирующую строку временной строкой
                    temp = ""; // обнулим временную строку
                    j = str.length(); // j сместим в конец строки
                }
            }
        }
        return res;
    }

    // Задание №8. Римское число
    public static String convertToRoman(int num) {
        String fs = ""; // Строка результат
        while(num != 0){ // Пока число не обработали(то есть не стало 0)
            if(num >= 1000){ // Если число больше 1000
                num -= 1000; // Отнимем 1000
                fs += "M"; // добавим букву M
            }
            else if(num >= 900){ // Если число больше 900, меньше 1000
                num -= 900; // Отнимем 900
                fs += "CM"; // добавим CM
            }
            else if(num >= 500){ // Если число больше 500
                num -= 500; // Отнимем 500
                fs += "D"; // добавим D
            }
            else if(num >= 400){ // Если число больше 400
                num -= 400; // Отнимем 400
                fs += "CD"; // добавим CD
            }
            else if(num >= 100){ // Если число больше 100
                num -= 100; // Отнимем 100
                fs += "C"; // добавим C
            }
            else if(num >= 90){ // Если число больше 90
                num -= 90; // Отнимем 90
                fs += "XC"; // добавим XC
            }
            else if(num >= 50){ // Если число больше 50
                num -= 50; // Отнимем 50
                fs += "L"; // добавим L
            }
            else if(num >= 40){ // Если число больше 40
                num -= 40; // Отнимем 40
                fs += "XL"; // добавим XL
            }
            else if(num >= 10){ // Если число больше 10
                num -= 10; // Отнимем 10
                fs += "X"; // добавим X
            }
            else if(num >= 9){ // Если число больше 9
                num -= 9; // Отнимем 9
                fs += "IX"; // добавим IX
            }
            else if(num >= 5){ // Если число больше 5
                num -= 5; // Отнимем 5
                fs += "V"; // добавим V
            }
            else if(num >= 4){ // Если число больше 4
                num -= 4; // Отнимем 4
                fs += "IV"; // добавим IV
            }
            else if(num > 0){ // Если число больше 1
                num -= 1;// Отнимем 1
                fs += "I"; // добавим I
            }
        }
        return fs;
    }

    // Задание №9. Верна ли формула?
    public static int calc(String str) {
        str = str.replaceAll("[()]", ""); // убираем круглые скобки
        str = str.replaceAll(" ", ""); // убираем пробелы
        try {
            return Integer.parseInt(str); // Пытаемся спарсить строку в число
        } catch (NumberFormatException e) { // Если проблема в формате числа
            String[] tokens; // Объявляем массив строк токенов
            tokens = str.split("\\*", 2);  // Пытаемся разбить на пару сомножетелей
            if (tokens.length > 1) { // Если токена 2, то производим умножение
                return calc(tokens[0]) * calc(tokens[1]);
            }
            tokens = str.split("/", 2); // Если не вышло, то пытаемся разбить по операции деления
            if (tokens.length > 1) { // Если токена 2, то производим деление
                return calc(tokens[0]) / calc(tokens[1]);
            }
            tokens = str.split("\\+", 2); // Если не вышло, то пытаемся разбить по операции сложения
            if (tokens.length > 1) { // Если токена 2, то производим деление
                return calc(tokens[0]) + calc(tokens[1]);
            }
            tokens = str.split("-", 2); // Если не вышло, то пытаемся разбить по операции вычитания
            if (tokens.length > 1) {// Если токена 2, то производим деление
                return calc(tokens[0]) - calc(tokens[1]);
            }
            return 0; // Если никак не вышло, вернем 0
        }
    }
    public static boolean formula(String str) {
        // Делим строку на строки, где разделителем является знак =
        String[] equations = str.split("=");
        for (int i = 0; i < equations.length-1; i++)// Обходим каждую такую строку "выражение"
        {
            if(calc(equations[i]) != calc(equations[i+1])) { // Если результат подсчета по формуле текущего и следуюшего значения не равны
                return false; // То возвращаем false
            }
        }
        // Если не срабатывало возвращаем true
        return true;

    }

    // Задание №10. Палиндром
    public static boolean palindromeDescendant(int num) {
        boolean isSym = false; // флаг полиндромности
        while(num > 9) { // Пока число не стало одноразрядное
            if(isSymmetrical(num)) { // Проверяем на симетричность
                isSym = true; // говорим, что оно полиндромно
                break; // останавливаем проверку
            }
            num = getSumofDigits(num); // Берем следующего потомка
        }
        return isSym;
    }
    public static boolean isSymmetrical(int num) {
        int reversenum = 0, n = num; // reversenum - обратное число n - в нормальном виде
        if(n < 0) { // Если число отрицательное
            n = n * -1; // Умножаем его на -1, то есть делаем положительным
        }
        while(n != 0) {  // Пока число не станет 0
            reversenum = reversenum * 10; // Умножаем обратное число на 10
            reversenum = reversenum + n % 10; // Прибавляем к обратному числу правую цифру самого числа
            n = n / 10; // убираем самую правую цифру
        }
        return(reversenum == num); // Если обратное число и само число равны, значит симетричны
    }
    public static int getSumofDigits(int n) {
        String iString = Integer.toString(n); // Приводим число к строке
        StringBuilder sb = new StringBuilder(); // Объявляем StringBuilder

        for(int i = 0; i < iString.length() - 1; i += 2) { // Обходим число по символам
            int i1 = Integer.parseInt(Character.toString(iString.charAt(i))); // Получаем index текущего
            int i2 = Integer.parseInt(Character.toString(iString.charAt(i+1))); // Получаем index слеудующего
            int num = i1 + i2; // Складываем индексы
            sb.append(num); // Добавим к строке сумму индексов
        }

        return Integer.parseInt(sb.toString()); // Преобразуем новую строку к числоу
    }
}