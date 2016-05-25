# SmartXML
Controlled Java Objects (De)Serialization from/to XML

### Benefits:  
- Easy to use  
- Flexibility  
- Support for properties accessible only through methods  
- Original classes remains unchanged  

### Examples:  

Here is a class to which we want add XML serialization support 
```
class ExampleOne {  
  private int a;  
  private int b;
  //this field is ignored
  private int c;
}  
```

This class is used to serialize to XML


```
class ExampleOneXML extends XML_PropertiesObject {

		public ExampleOneXML() {
			this("ExampleOne", new ExampleOne());
		}

		public ExampleOneXML(String name) {
			this(name, new ExampleOne());
		}

		public ExampleOneXML(ExampleOne obj) {
			this("ExampleOne", obj);
		}

		public ExampleOneXML(String name, ExampleOne obj) {
			super("ExampleOne", obj);
			try {
				addFieldProperty("a");
				addFieldProperty("b");
			} catch (NoSuchFieldException ex) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
```

	This class is used to deserialize from XML
	
```
	class ExampleOneXmlSupport implements XML_Support {
		private ExampleOne exampleOne;

		public ExampleOneXmlSupport() {
			this(null);
		}

		public ExampleOneXmlSupport(ExampleOne exampleOne) {
			this.exampleOne = exampleOne;
		}

		@Override
		public XML_Properties create() {
			if (exampleOne != null) {
				return new ExampleOneXML(exampleOne);
			}
			return new ExampleOneXML();
		}

		@Override
		public XML_Properties create(String name) {
			if (exampleOne != null) {
				return new ExampleOneXML(name, exampleOne);
			}
			return new ExampleOneXML(name, new ExampleOne());
		}
	}
```
The two classes above seems big compared to class we want to serialize.  
However they are simple and may be generated (in the future).


```
	public static void main(String[] args) {
		ExampleOne ex = new ExampleOne();

		ex.a = 7;
		ex.b = 8;
		ex.c = 3;

  //serialize using just two lines
		ExampleOneXML eoXml = new ExampleOneXML(ex);
		String xml = XML_Util.toXml(eoXml);
		
		System.out.println(xml);

  //deserialize using just three lines
		ExampleOneXmlSupport xmlSupport = new ExampleOneXmlSupport();
		XML_Properties parseXml = XML_Util.parseXml(xml, "ExampleOne", xmlSupport);
		ExampleOne newOne = (ExampleOne) parseXml.getObject();
		
		System.out.println(newOne.a + " " + newOne.b + " " + newOne.c);
	}
	```
