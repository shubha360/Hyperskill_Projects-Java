<h2>Description</h2>

<p>In the previous stage, you've expanded an XML file, and in this stage you will expand a JSON file.</p>

<p>To parse it you should write a recursive function that will check if the JSON value starts with a curly brace that can be presented as a start of child nodes and continue to check subkeys.</p>

<p>JSON with sub-elements would look like this:</p>

<pre><code class="language-json">{
    "key": {
        "child_key1": "child_key_value",
        "child_key2": "child_key_value",
        "child_key3": {
            "child_child_key": "value"
        },
        "child_key4": {
            "child_child_key1": "value1",
            "child_child_key2": "value2"
        }
    }
}</code></pre>

<p>The algorithm for traversing JSON is similar to traversing XML.</p>

<p>Let’s add some magic constants <code class="language-json">@</code> and <code class="language-json">#</code> to represent attributes and values. Now the JSON tree would look like this:</p>

<pre><code class="language-json">{
    "key": {
        "child_key1": {
            "@attribute1": "value1",
            "@attribute2": "value2",
            "#child_key1": "value3"
        },
        "child_key2": "child_key_value",
        "child_key3": {
            "@attribute1": "value4",
            "@attribute2": "value5",
            "#child_key3": null
        },
        "child_key4": {
            "child_child_key1": "value1",
            "child_child_key2": "value2"
        }
    }
}</code></pre>

<p>Parsing JSON attributes is different from parsing XML. As you can see, every attribute and corresponding value can be presented as child_nodes for every element. That means you just need to expand your traversal of the JSON tree by checking if its subchildren start with <code class="language-json">@</code> or <code class="language-json">#</code>.</p>

<p>However, there is a problem: what would you do if the value contains keys that start with <code class="language-json">@</code> but there are no keys that start with <code class="language-json">#</code>? Similarly, what would you do if the value contains keys that start with <code class="language-json">#</code> along with keys that start with <code class="language-json">@</code> but there are also keys that start with something else?</p>

<p>In these cases, you should forget about the special meaning of <code class="language-json">@</code> and <code class="language-json">#</code> in front of a key and look at them as regular keys with regular values. You should remove <code class="language-json">@</code> and <code class="language-json">#</code> from the key as these symbols are not allowed in the XML tag names.</p>

<p>Also, if the key contains only the <code class="language-json">@</code> symbol, then it's not considered a special key representing an attribute.</p>

<p>For the keys that lost their meaning due to some errors, you should omit <code class="language-json">@</code> and <code class="language-json">#</code> symbols from the start as XML elements can't start with these symbols. If there are <code class="language-json">@key</code> and <code class="language-json">key</code> in the object, you should prefer a key without the <code class="language-json">@</code> symbol.</p>

<p>Also, avoid empty keys. Do not map them to the output as they definitely would break something during conversion to an XML. Keep in mind that if the key contains only the <code class="language-json">@</code> symbol, then it's not considered a special key representing an attribute and after omitting this symbol key would be empty. Therefore, you shouldn't convert keys <code class="language-json">@</code> and <code class="language-json">#</code>.</p>

<p>Also, if the attribute is null, change it to the empty string.</p>

<p>So, in short, you need to consider a JSON element as an object with attributes only if:</p>

<p>1. The object has a key with the same name as the object, with a <code class="language-json">#</code> symbol in front of it. For example, if the key of the object is <code class="language-json">"obj"</code> then the value of this object <strong>has to be inside</strong> <code class="language-json">"#obj"</code> key inside this object. Note that if such a key does not exist in the object, this object <strong>should not be considered</strong> a single XML object with attributes.</p>

<p>2. The <code class="language-json">value</code> object contains the <code class="language-json">#value</code> key and <strong>all other attributes</strong> begin with the <code class="language-json">@</code> symbol and <strong>are longer than 1 character</strong>. If this object has at least one key that equals <code class="language-json">@</code> or does not start with <code class="language-json">@</code> (except <code class="language-json">#value</code>), then this object <strong>should not be considered</strong> a single XML object with attributes.</p>

<p>3. If the value of any key starting with <code class="language-json">@</code> is not a number, string or null (in other words, <strong>it will be an object</strong> starting with <code class="language-json">"{"</code>), then this object <strong>cannot be</strong> an attribute of a single XML object and the <code class="language-json">@</code> symbol should be removed from this key, and thus the object <strong>cannot be considered</strong> a single XML object.</p>

<p>The object without attributes would look like a standard JSON object, without <code class="language-json">@</code> or <code class="language-json">#</code> symbols around. In particular, all wrong cases of creating an object with attributes result in a standard object after certain manipulations. For example <code class="language-json">inner1</code>, <code class="language-json">inner2</code> and <code class="language-json">inner3</code> from the first example are objects without attributes with no errors in construction.</p>

<p>Some cases from the first example:</p>

<p>1. <code class="language-json">inner4</code> contains a key <code class="language-json">@</code> and it is not an attribute. That's why this object is not considered a single object with attributes. But since <code class="language-json">@</code> key is not allowed, this key would be removed and <code class="language-json">#inner4</code> key will turn into <code class="language-json">inner4</code> key. After all the manipulations, this object will look like this:</p>

<pre><code class="language-json">"inner4": {
    "inner4": "value3"
}</code></pre>

<p>Same rules apply to <code class="language-json">inner4.2</code>.</p>

<p>2. <code class="language-json">inner5</code> contains key <code class="language-json">#inner4</code> but should contain key <code class="language-json">#inner5</code> to be a single object with attributes.</p>

<p>3. <code class="language-json">inner6</code> constructed in the correct way, with <code class="language-json">#inner6</code> key and an attribute that starts with <code class="language-json">@</code>. That's why <code class="language-json">inner6</code> is a valid single XML object with attributes.</p>

<p>4. <code class="language-json">inner7</code> constructed in the correct way, with <code class="language-json">#inner7</code> key, just without attributes and that's allowed.</p>

<p>5. <code class="language-json">inner8</code> doesn't contain key <code class="language-json">#inner8</code> and should not be considered a single XML object with attributes.</p>

<p>6. <code class="language-json">inner11</code> contains children, all their names are not allowed, so these children will be deleted and <code class="language-json">inner11</code> will become an empty object. Similar to <code class="language-json">empty2</code>, the value of this object has to be an empty string.</p>

<p>All of the variants are described in the output example below.</p>

<p>In this stage you should write a program that reads JSON file from disk, parses it and outputs the following:</p>

<pre><code class="language-json">Key:
path = super_key … parent_key
value = value
attributes : [ if key doesn’t contain attributes skip this ]
attributeN = attributeN</code></pre>

<p>Looking ahead, how are you going to convert this XML into JSON?</p>

<pre><code class="language-xml">&lt;elem1 attr1="val1" attr2="val2"&gt;
    &lt;elem2 attr3="val3" attr4="val4"&gt;Value1&lt;/elem2&gt;
    &lt;elem3 attr5="val5" attr6="val6"&gt;Value2&lt;/elem3&gt;
&lt;/elem1&gt;</code></pre>

<p>Note that the element <code class="language-json">elem1</code> contains attributes, but its value is on another level of complexity, not just a number, null, or a string. Well, in this case you can also use standard rules. See how it should be converted into JSON:</p>

<pre><code class="language-json">{
    "elem1": {
        "@attr1": "val1",
        "@attr2": "val2",
        "#elem1": {
            "elem2": {
                "@attr3": "val3",
                "@attr4": "val4",
                "#elem2": "Value1"
            },
            "elem3": {
                "@attr5": "val5",
                "@attr6": "val6",
                "#elem3": "Value2"
            }
        }
    }
}</code></pre>

<p>Also, you should not rely on new lines, they are only there to make the code more readable. Take a look at the last output example: the initial JSON file doesn't contain any new lines, everything is on a single line although the content is equal to the last but one example.</p>

<p>You should read from the file named <code class="language-json">test.txt</code>. If you want to test your program, you should check it on some other file, because the contents of this file will be overwritten during testing and after testing the file will be deleted.</p>

<h2>Examples</h2>

<p><strong>Example 1:</strong></p>

<p>Input example</p>

<pre><code class="language-json">{
    "transaction": {
        "id": "6753322",
        "number": {
            "@region": "Russia",
            "#number": "8-900-000-000"
        },
        "empty1": null,
        "empty2": { },
        "empty3": "",
        "inner1": {
            "inner2": {
                "inner3": {
                    "key1": "value1",
                    "key2": "value2"
                }
            }
        },
        "inner4": {
            "@": 123,
            "#inner4": "value3"
        },
        "inner4.2": {
            "": 123,
            "#inner4.2": "value3"
        },
        "inner5": {
            "@attr1": 123.456,
            "#inner4": "value4"
        },
        "inner6": {
            "@attr2": 789.321,
            "#inner6": "value5"
        },
        "inner7": {
            "#inner7": "value6"
        },
        "inner8": {
            "@attr3": "value7"
        },
        "inner9": {
            "@attr4": "value8",
            "#inner9": "value9",
            "something": "value10"
        },
        "inner10": {
            "@attr5": null,
            "#inner10": null
        },
        "inner11": {
            "@": null,
            "#": null
        },
        "inner12": {
            "@somekey": "attrvalue",
            "#inner12": null,
            "somekey": "keyvalue",
            "inner12": "notnull"
        },
        "inner13": {
            "@invalid_attr": {
                "some_key": "some value"
            },
            "#inner13": {
                "key": "value"
            }
        },
        "": {
            "#": null,
            "secret": "this won't be converted"
        }
    },
    "meta": {
        "version": 0.01
    }
}</code></pre>

<p>Output example</p>

<pre><code class="language-json">Element:
path = transaction

Element:
path = transaction, id
value = "6753322"

Element:
path = transaction, number
value = "8-900-000-000"
attributes:
region = "Russia"

Element:
path = transaction, empty1
value = null

Element:
path = transaction, empty2
value = ""

Element:
path = transaction, empty3
value = ""

Element:
path = transaction, inner1

Element:
path = transaction, inner1, inner2

Element:
path = transaction, inner1, inner2, inner3

Element:
path = transaction, inner1, inner2, inner3, key1
value = "value1"

Element:
path = transaction, inner1, inner2, inner3, key2
value = "value2"

Element:
path = transaction, inner4

Element:
path = transaction, inner4, inner4
value = "value3"

Element:
path = transaction, inner4.2

Element:
path = transaction, inner4.2, inner4.2
value = "value3"

Element:
path = transaction, inner5

Element:
path = transaction, inner5, attr1
value = "123.456"

Element:
path = transaction, inner5, inner4
value = "value4"

Element:
path = transaction, inner6
value = "value5"
attributes:
attr2 = "789.321"

Element:
path = transaction, inner7
value = "value6"

Element:
path = transaction, inner8

Element:
path = transaction, inner8, attr3
value = "value7"

Element:
path = transaction, inner9

Element:
path = transaction, inner9, attr4
value = "value8"

Element:
path = transaction, inner9, inner9
value = "value9"

Element:
path = transaction, inner9, something
value = "value10"

Element:
path = transaction, inner10
value = null
attributes:
attr5 = ""

Element:
path = transaction, inner11
value = ""

Element:
path = transaction, inner12

Element:
path = transaction, inner12, somekey
value = "keyvalue"

Element:
path = transaction, inner12, inner12
value = "notnull"

Element:
path = transaction, inner13

Element:
path = transaction, inner13, invalid_attr

Element:
path = transaction, inner13, invalid_attr, some_key
value = "some value"

Element:
path = transaction, inner13, inner13

Element:
path = transaction, inner13, inner13, key
value = "value"

Element:
path = meta

Element:
path = meta, version
value = "0.01"</code></pre>

<p><strong>Example 2:</strong></p>

<p>Input example</p>

<pre><code class="language-json">{
    "root1": {
        "@attr1": "val1",
        "@attr2": "val2",
        "#root1": {
            "elem1": {
                "@attr3": "val3",
                "@attr4": "val4",
                "#elem1": "Value1"
            },
            "elem2": {
                "@attr5": "val5",
                "@attr6": "val6",
                "#elem2": "Value2"
            }
        }
    },
    "root2": {
        "@attr1": null,
        "@attr2": "",
        "#root2": null
    },
    "root3": {
        "@attr1": "val2",
        "@attr2": "val1",
        "#root3": ""
    },
    "root4": "Value4"
}</code></pre>

<p>Output example</p>

<pre><code class="language-json">Element:
path = root1
attributes:
attr1 = "val1"
attr2 = "val2"

Element:
path = root1, elem1
value = "Value1"
attributes:
attr3 = "val3"
attr4 = "val4"

Element:
path = root1, elem2
value = "Value2"
attributes:
attr5 = "val5"
attr6 = "val6"

Element:
path = root2
value = null
attributes:
attr1 = ""
attr2 = ""

Element:
path = root3
value = ""
attributes:
attr1 = "val2"
attr2 = "val1"

Element:
path = root4
value = "Value4"</code></pre>

<p><strong>Example 3:</strong></p>

<p>Input example</p>

<pre><code class="language-json">{"root1":{"@attr1":"val1","@attr2":"val2","#root1":{"elem1":{"@attr3":"val3","@attr4":"val4","#elem1":"Value1"},"elem2":{"@attr5":"val5","@attr6":"val6","#elem2":"Value2"}}},"root2":{"@attr1":null,"@attr2":"","#root2":null},"root3":{"@attr1":"val2","@attr2":"val1","#root3":""},"root4":"Value4"}</code></pre>

<p>Output example</p>

<pre><code class="language-json">Element:
path = root1
attributes:
attr1 = "val1"
attr2 = "val2"

Element:
path = root1, elem1
value = "Value1"
attributes:
attr3 = "val3"
attr4 = "val4"

Element:
path = root1, elem2
value = "Value2"
attributes:
attr5 = "val5"
attr6 = "val6"

Element:
path = root2
value = null
attributes:
attr1 = ""
attr2 = ""

Element:
path = root3
value = ""
attributes:
attr1 = "val2"
attr2 = "val1"

Element:
path = root4
value = "Value4"</code></pre>