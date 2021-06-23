<h2>Description</h2>

<p>In this stage, you will expand XML parsing by adding sub-elements like this:</p>

<pre><code class="language-xml">&lt;node&gt;
    &lt;child&gt;
        &lt;subchild&gt;&lt;/subchild&gt;
        ...
        &lt;subchild&gt;&lt;/subchild&gt;
    &lt;/child&gt;
    &lt;child&gt;
        &lt;subchild&gt;&lt;/subchild&gt;
        ...
        &lt;subchild&gt;&lt;/subchild&gt;
    &lt;/child&gt;
    ...
&lt;/node&gt;</code></pre>

<p>You need to create a function that will parse and traverse an XML document. You don't need to use parsing libraries, you need to write a parser by yourself.</p>

<p>In the previous stage, you’ve learned how to parse attributes. When you traverse an XML tree, you need to check if an element contains attributes. To do this, you can substring data between opening and closing tags and split every attribute data into keys and values.</p>

<pre><code class="language-xml">&lt;element | attribute1="value1" |  …  | attributeN="valueN" |/&gt;</code></pre>

<p>XML tree with attributes looks like this:</p>

<pre><code class="language-xml">&lt;node&gt;
    &lt;child name = "child_name1" type = "child_type1"&gt;
        &lt;subchild id = "1" auth="auth1"&gt;Value1&lt;/subchild&gt;
    &lt;/child&gt;
    &lt;child name = "child_name2" type = "child_type2"&gt;
        &lt;subchild id = "2" auth="auth1"&gt;Value2&lt;/subchild&gt;
        &lt;subchild id = "3" auth="auth2"&gt;Value3&lt;/subchild&gt;
        &lt;subchild id = "4" auth="auth3"&gt;&lt;/subchild&gt;
        &lt;subchild id = "5" auth="auth3"/&gt;
    &lt;/child&gt;
&lt;/node&gt;</code></pre>

<p>In every step of XML tree traversal, check subchildren and attributes. Note that the elements can be empty or can lack attributes.</p>

<p>In this stage you should improve the program; it should read the XML file from the disk, parse it, and output its contents in this format:</p>

<pre><code class="language-no-highlight">Element:
path = grand_parent, ... , parent
value = element_value [ null if it’s empty ]
attributes: [ Only if element contain attributes ]
attributeN = valueN</code></pre>

<p>For every element in XML, you should print its hierarchy from the root parent to the direct parent element separated by commas and attribute lists.</p>

<p>If the element can't have value (e.g. parent element) you should omit value from the output.</p>

<pre><code class="language-no-highlight">Element:
path = grand_parent, ... , parent
attributes: [ Only if element contain attributes ]
attributeN = valueN</code></pre>

<p>Also, you should not rely on new lines, because they are only there to make the code more readable to a human. Take a look at the last output example: the initial XML file doesn't have any new lines. Everything is presented in a single line although the content is the same as in the last but one example.</p>

<p>You should read from the file named <code class="language-xml">test.txt</code>. If you want to test your program, you should check it on some other file, because the contents of this file will be overwritten during testing and after testing the file will be deleted.</p>

<h2>Examples</h2>

<p><strong>Example 1:</strong></p>

<p>Input example</p>

<pre><code class="language-xml">&lt;transaction&gt;
    &lt;id&gt;6753322&lt;/id&gt;
    &lt;number region="Russia"&gt;8-900-000-00-00&lt;/number&gt;
    &lt;nonattr /&gt;
    &lt;nonattr&gt;&lt;/nonattr&gt;
    &lt;nonattr&gt;text&lt;/nonattr&gt;
    &lt;attr id="1" /&gt;
    &lt;attr id="2"&gt;&lt;/attr&gt;
    &lt;attr id="3"&gt;text&lt;/attr&gt;
    &lt;email&gt;
        &lt;to&gt;to_example@gmail.com&lt;/to&gt;
        &lt;from&gt;from_example@gmail.com&lt;/from&gt;
        &lt;subject&gt;Project discussion&lt;/subject&gt;
        &lt;body font="Verdana"&gt;Body message&lt;/body&gt;
        &lt;date day="12" month="12" year="2018"/&gt;
    &lt;/email&gt;
&lt;/transaction&gt;</code></pre>

<p>Output example</p>

<pre><code class="language-no-highlight">Element:
path = transaction

Element:
path = transaction, id
value = "6753322"

Element:
path = transaction, number
value = "8-900-000-00-00"
attributes:
region = "Russia"

Element:
path = transaction, nonattr
value = null

Element:
path = transaction, nonattr
value = ""

Element:
path = transaction, nonattr
value = "text"

Element:
path = transaction, attr
value = null
attributes:
id = "1"

Element:
path = transaction, attr
value = ""
attributes:
id = "2"

Element:
path = transaction, attr
value = "text"
attributes:
id = "3"

Element:
path = transaction, email

Element:
path = transaction, email, to
value = "to_example@gmail.com"

Element:
path = transaction, email, from
value = "from_example@gmail.com"

Element:
path = transaction, email, subject
value = "Project discussion"

Element:
path = transaction, email, body
value = "Body message"
attributes:
font = "Verdana"

Element:
path = transaction, email, date
value = null
attributes:
day = "12"
month = "12"
year = "2018"</code></pre>

<p><strong>Example 2:</strong></p>

<p>Input example</p>

<pre><code class="language-xml">&lt;node&gt;
    &lt;child name = "child_name1" type = "child_type1"&gt;
        &lt;subchild id = "1" auth="auth1"&gt;Value1&lt;/subchild&gt;
    &lt;/child&gt;
    &lt;child name = "child_name2" type = "child_type2"&gt;
        &lt;subchild id = "2" auth="auth1"&gt;Value2&lt;/subchild&gt;
        &lt;subchild id = "3" auth="auth2"&gt;Value3&lt;/subchild&gt;
        &lt;subchild id = "4" auth="auth3"&gt;&lt;/subchild&gt;
        &lt;subchild id = "5" auth="auth3"/&gt;
    &lt;/child&gt;
&lt;/node&gt;</code></pre>

<p>Output example</p>

<pre><code class="language-no-highlight">Element:
path = node

Element:
path = node, child
attributes:
name = "child_name1"
type = "child_type1"

Element:
path = node, child, subchild
value = "Value1"
attributes:
id = "1"
auth = "auth1"

Element:
path = node, child
attributes:
name = "child_name2"
type = "child_type2"

Element:
path = node, child, subchild
value = "Value2"
attributes:
id = "2"
auth = "auth1"

Element:
path = node, child, subchild
value = "Value3"
attributes:
id = "3"
auth = "auth2"

Element:
path = node, child, subchild
value = ""
attributes:
id = "4"
auth = "auth3"

Element:
path = node, child, subchild
value = null
attributes:
id = "5"
auth = "auth3"</code></pre>

<p><strong>Example 3:</strong></p>

<p>Input example</p>

<pre><code class="language-xml">&lt;node&gt;&lt;child name="child_name1" type="child_type1"&gt;&lt;subchild id="1" auth="auth1"&gt;Value1&lt;/subchild&gt;&lt;/child&gt;&lt;child name="child_name2" type="child_type2"&gt;&lt;subchild id="2" auth="auth1"&gt;Value2&lt;/subchild&gt;&lt;subchild id="3" auth="auth2"&gt;Value3&lt;/subchild&gt;&lt;subchild id="4" auth="auth3"&gt;&lt;/subchild&gt;&lt;subchild id="5" auth="auth3"/&gt;&lt;/child&gt;&lt;/node&gt;</code></pre>

<p>Output example</p>

<pre><code class="language-no-highlight">Element:
path = node

Element:
path = node, child
attributes:
name = "child_name1"
type = "child_type1"

Element:
path = node, child, subchild
value = "Value1"
attributes:
id = "1"
auth = "auth1"

Element:
path = node, child
attributes:
name = "child_name2"
type = "child_type2"

Element:
path = node, child, subchild
value = "Value2"
attributes:
id = "2"
auth = "auth1"

Element:
path = node, child, subchild
value = "Value3"
attributes:
id = "3"
auth = "auth2"

Element:
path = node, child, subchild
value = ""
attributes:
id = "4"
auth = "auth3"

Element:
path = node, child, subchild
value = null
attributes:
id = "5"
auth = "auth3"</code></pre>