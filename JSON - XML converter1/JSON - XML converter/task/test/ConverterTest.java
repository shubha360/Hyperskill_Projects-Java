import com.google.gson.*;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.*;

import java.math.BigDecimal;
import java.util.*;

class Clue {
    String answer;
    String input;
    boolean showFeedback = false;

    Clue(String answer, String input) {
        this.answer = answer.strip();
        this.input = input.strip();
    }

    String getFeedback() {
        if (!showFeedback) {
            return "";
        }
        return "This test is exactly like in the examples";
    }
}

public class ConverterTest extends StageTest<Clue> {

    static Map<String, String> allTests;

    static {
        allTests = new LinkedHashMap<>();

        allTests.put(
            "<employee department = \"manager\">Garry Smith</employee>",

            "{\n" +
            "    \"employee\" : {\n" +
            "        \"@department\" : \"manager\",\n" +
            "        \"#employee\" : \"Garry Smith\"\n" +
            "    }\n" +
            "}"
        );

        allTests.put(
            "<person rate = \"1\" name = \"Torvalds\" />",

            "{\n" +
            "    \"person\" : {\n" +
            "        \"@rate\" : \"1\",\n" +
            "        \"@name\" : \"Torvalds\",\n" +
            "        \"#person\" : null\n" +
            "    }\n" +
            "}"
        );

        allTests.put(
            "{\n" +
            "    \"employee\" : {\n" +
            "        \"@department\" : \"manager\",\n" +
            "        \"#employee\" : \"Garry Smith\"\n" +
            "    }\n" +
            "}",

            "<employee department = \"manager\">Garry Smith</employee>"
        );

        allTests.put(
            "{\n" +
            "    \"person\" : {\n" +
            "        \"@rate\" : 1,\n" +
            "        \"@name\" : \"Torvalds\",\n" +
            "        \"#person\" : null\n" +
            "    }\n" +
            "}",

            "<person rate = \"1\" name = \"Torvalds\" />"
        );

        allTests.put(
            "{\n" +
            "    \"pizza\" : {\n" +
            "        \"@size\" : 20,\n" +
            "        \"#pizza\" : 123\n" +
            "    }\n" +
            "}",

            "<pizza size = \"20\">123</pizza>"
        );

        allTests.put(
            "<pizza size = \"20\">123</pizza>",

            "{\n" +
            "    \"pizza\" : {\n" +
            "        \"@size\" : \"20\",\n" +
            "        \"#pizza\" : \"123\"\n" +
            "    }\n" +
            "}"
        );
    }

    @Override
    public List<TestCase<Clue>> generate() {

        List<TestCase<Clue>> tests = new ArrayList<>();

        int i = 0;
        for (String input : allTests.keySet()) {
            String answer = allTests.get(input);

            TestCase<Clue> test = new TestCase<>();

            test.addFile("test.txt", input);
            test.setAttach(new Clue(answer, input));

            if (i < 4) {
                test.getAttach().showFeedback = true;
            }
            i++;

            tests.add(test);
        }

        return tests;
    }

    @Override
    public CheckResult check(String reply, Clue clue) {

        String user = reply.strip();
        String answer = clue.answer.strip();

        if (user.length() == 0) {
            return new CheckResult(false,
                "Your output is empty line.");
        }

        CheckResult result;

        if (user.charAt(0) != '<' && user.charAt(0) != '{') {
            return new CheckResult(false,
                "Your first symbol is wrong - " +
                    " should be '{' or '<'");
        }

        try {
            if (user.charAt(0) == '<' && answer.charAt(0) == '<') {
                result = isEqualXMLs(user, answer);
            } else if (user.charAt(0) == '{' && answer.charAt(0) == '{') {
                result = isEqualJSONs(user, answer);
            } else {
                return new CheckResult(false,
                    "Your first symbol is wrong - " +
                        "'{' instead of '<' or vice versa" + "\n\n" + user + "\n\n" + answer);
            }
        } catch (Exception ex) {
            return new CheckResult(false,
                "Can't check the output - invalid XML or JSON");
        }

        if (result.getFeedback().length() != 0) {
            result = new CheckResult(
                result.isCorrect(),
                clue.getFeedback() + "\n" + result.getFeedback());
        }
        return result;
    }

    public Element stringToXML(String str) throws Exception {
        DocumentBuilderFactory factory =
            DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        ByteArrayInputStream input = new ByteArrayInputStream(
            str.getBytes("UTF-8"));

        Document document = builder.parse(input);

        return document.getDocumentElement();
    }


    public CheckResult isEqualXMLs(String s1, String s2) throws Exception {
        Element elem1 = stringToXML(s1);
        Element elem2 = stringToXML(s2);

        CheckResult result = isEqualXMLElements(elem1, elem2);

        if (!result.isCorrect()) {
            return result;
        } else {
            return isEqualXMLElements(elem2, elem1);
        }
    }

    public static CheckResult isEqualXMLElements(Element e1, Element e2) {
        // test name
        if (!e1.getNodeName().equals(e2.getNodeName())) {
            return new CheckResult(false,
                "In XML: element name is incorrect");
        }

        // test attributes
        NamedNodeMap attributes = e1.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Attr attr = (Attr) attributes.item(i);

            String name = attr.getName();

            if (!e2.hasAttribute(name)) {
                return new CheckResult(false,
                    "In XML: element doesn't have " +
                        "an attribute or has an excess attribute");
            }

            if (!attr.getValue().equals(e2.getAttribute(name))) {
                return new CheckResult(false,
                    "In XML: element has an attribute " +
                        "but their values don't match");
            }
        }

        // test chidls
        if (e1.hasChildNodes() != e2.hasChildNodes()) {
            return new CheckResult(false,
                "In XML: element doesn't have needed " +
                    "child nodes or has excess child nodes");
        }

        if (!e1.hasChildNodes()) {
            return CheckResult.correct();
        }

        NodeList childs1 = e1.getChildNodes();
        NodeList childs2 = e2.getChildNodes();

        List<Element> filteredChilds1 = new ArrayList<>();
        List<Element> filteredChilds2 = new ArrayList<>();

        for (int i = 0; i < childs1.getLength(); i++) {
            Object item = childs1.item(i);
            if (item instanceof Element) {
                filteredChilds1.add((Element) childs1.item(i));
            }
        }

        for (int i = 0; i < childs2.getLength(); i++) {
            Object item = childs2.item(i);
            if (item instanceof Element) {
                filteredChilds2.add((Element) childs2.item(i));
            }
        }

        if (filteredChilds1.size() != filteredChilds2.size()) {
            return new CheckResult(false,
                "In XML: element doesn't have needed " +
                    "child nodes or has excess child nodes");
        }

        for (int i = 0; i < filteredChilds1.size(); i++) {

            Element elem1 = filteredChilds1.get(i);
            Element elem2 = filteredChilds2.get(i);

            CheckResult result = isEqualXMLElements(elem1, elem2);
            if (!result.isCorrect()) {
                return result;
            }
        }

        return CheckResult.correct();
    }

    public JsonElement stringToJSON(String str) {
        return new JsonParser().parse(str);
    }


    public CheckResult isEqualJSONs(String s1, String s2) {
        JsonElement elem1 = stringToJSON(s1);
        JsonElement elem2 = stringToJSON(s2);

        CheckResult result = isEqualJSONElements(elem1, elem2);

        if (!result.isCorrect()) {
            return result;
        } else {
            return isEqualJSONElements(elem2, elem1);
        }
    }

    public CheckResult isEqualJSONElements(JsonElement e1, JsonElement e2) {

        // check for null
        if (e1.isJsonNull() != e2.isJsonNull()) {
            return new CheckResult(false,
                "In JSON: expected null but found something else " +
                    "(or vice versa)");
        }
        if (e1.isJsonNull()) {
            return CheckResult.correct();
        }


        // check for primitives
        if (e1.isJsonPrimitive() != e2.isJsonPrimitive()) {
            // number and boolean are also may be expected but
            // after converting from XML there can be only strings
            return new CheckResult(false,
                "In JSON: expected string " +
                    "but found something else (or vice versa)");
        }
        if (e1.isJsonPrimitive()) {
            JsonPrimitive prim1 = e1.getAsJsonPrimitive();
            JsonPrimitive prim2 = e2.getAsJsonPrimitive();
            return compareJSONPrimitives(prim1, prim2);
        }


        // check for arrays
        if (e1.isJsonArray() != e2.isJsonArray()) {
            return new CheckResult(false,
                "In JSON: expected array " +
                    "but found something else (or vice versa)");
        }
        if (e1.isJsonArray()) {
            JsonArray arr1 = e1.getAsJsonArray();
            JsonArray arr2 = e2.getAsJsonArray();
            return compareJSONArrays(arr1, arr2);
        }


        // check for objects
        if (e1.isJsonObject() != e2.isJsonObject()) {
            return new CheckResult(false,
                "In JSON: expected object " +
                    "but found something else (or vice versa)");
        }
        if (e1.isJsonObject()) {
            JsonObject obj1 = e1.getAsJsonObject();
            JsonObject obj2 = e2.getAsJsonObject();
            return compareJSONObjects(obj1, obj2);
        }

        return CheckResult.correct();
    }


    public CheckResult compareJSONPrimitives(JsonPrimitive prim1,
                                             JsonPrimitive prim2) {

        if (prim1.isBoolean() && prim2.isBoolean()) {
            return new CheckResult(
                prim1.getAsBoolean() == prim2.getAsBoolean(),
                "In JSON: two boolean values don't match");
        }
        if (prim1.isNumber() && prim2.isNumber()) {
            BigDecimal num1 = prim1.getAsBigDecimal();
            BigDecimal num2 = prim2.getAsBigDecimal();
            return new CheckResult(num1.equals(num2),
                "In JSON: two number values don't match");
        }
        if (prim1.isString() && prim2.isString()) {
            String num1 = prim1.getAsString();
            String num2 = prim2.getAsString();
            return new CheckResult(num1.equals(num2),
                "In JSON: two string values don't match");
        }


        if (prim1.isString() && prim2.isNumber() ||
            prim1.isNumber() && prim2.isString()) {

            return new CheckResult(false,
                "In JSON: expected string value but " +
                    "found number (or vice versa)");
        }
        if (prim1.isString() && prim2.isBoolean() ||
            prim1.isBoolean() && prim2.isString()) {

            return new CheckResult(false,
                "In JSON: expected string value but " +
                    "found boolean (or vice versa)");
        }
        if (prim1.isNumber() && prim2.isBoolean() ||
            prim1.isBoolean() && prim2.isNumber()) {

            return new CheckResult(false,
                "In JSON: expected number value but " +
                    "found boolean (or vice versa)");
        }

        return CheckResult.correct();
    }

    public CheckResult compareJSONArrays(JsonArray arr1, JsonArray arr2) {
        if (arr1.size() != arr2.size()) {
            return new CheckResult(false,
                "In JSON: array size is incorrect");
        }

        for (int i = 0; i < arr1.size(); i++) {
            JsonElement elem1 = arr1.get(i);
            JsonElement elem2 = arr2.get(i);

            CheckResult result = isEqualJSONElements(elem1, elem2);
            if (!result.isCorrect()) {
                return result;
            }
        }

        return CheckResult.correct();
    }

    public CheckResult compareJSONObjects(JsonObject obj1, JsonObject obj2) {

        for (String key : obj1.keySet()) {
            if (!obj2.has(key)) {
                return new CheckResult(false,
                    "In JSON: object doesn't have " +
                        "needed key or has an excess key");
            }

            JsonElement value1 = obj1.get(key);
            JsonElement value2 = obj2.get(key);

            CheckResult result = isEqualJSONElements(value1, value2);
            if (!result.isCorrect()) {
                return result;
            }
        }

        return CheckResult.correct();
    }

}
