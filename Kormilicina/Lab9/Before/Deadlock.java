public class Deadlock 
{
    static class People 
	{
        private final String name;
        public People(String name) 
		{
            this.name = name;
        }
        public String getName() 
		{
            return this.name;
        }
        public synchronized void talk(People talking) 
		{
            System.out.printf("%s:" + "  I can't talk to %s!%n", this.name, talking.getName());
            talking.bowBack(this);
        }
        public synchronized void talkBack(People talking) 
		{
            System.out.printf("%s:" + "  I can't talk to %s!%n", this.name, talking.getName());
        }
    }    
    public static void Main(String[] args) 
	{
        final People person1 = new People("Person1");
        final People person2 = new People("Person2");
        
        new Thread(new Runnable() {
            public void run()
                {
                    person1.talk(person2);
                }
        }).start();
        new Thread(new Runnable() {
            public void run()
                {
                    person1.talk(person2);
                }
        }).start();
    }
}
