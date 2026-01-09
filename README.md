# java-program-basic //Day1
# Writing a program using class person to display name age
class Person
{
  String name;
  int age;
}   //Class person
class myprgramperson {
  public static void main(String[] args) {
    Person p1=new Person();
    Person p2=new Person();
    p1.name="modi";
    p1.age=71;
    p2.name="bachchan";
    p2.age=80;
    System.out.println("p1.name="+p1.name);
    System.out.println("p2.name="+p2.name);
    System.out.println("p1.age="+p1.age);
    System.out.println("p2.age="+p2.age);
    }  //main()
}


# Create a class template
class boxdemo{
	double width;
	double height;
	double depth;
}
class boxdemo{
public static void main main(String args[]){
	box mybox=new box();
	double vol;
	mybox.width=10;
	mybox.height=20;
	mybox.depth=15;
    vol=mybox.width*mybox.height*mybox.depth;
	System.out.println("Volume is " +vol);
	}
}


//Day 2
# This program declares two Box objects.
class Box {
    double width;
    double height;
    double depth;
}

class BoxDemo2 {
    public static void main(String args[]) {
        Box mybox1 = new Box();
        Box mybox2 = new Box();
        double vol;

 // assign values to mybox1's instance variables
        mybox1.width = 10;
        mybox1.height = 20;
        mybox1.depth = 15;

/* assign different values to mybox2's
           instance variables */
        mybox2.width = 3;
        mybox2.height = 6;
        mybox2.depth = 9;

 // compute volume of first box
        vol = mybox1.width * mybox1.height * mybox1.depth;
        System.out.println("Volume is " + vol);

// compute volume of second box
        vol = mybox2.width * mybox2.height * mybox2.depth;
        System.out.println("Volume is " + vol);
    }
}

