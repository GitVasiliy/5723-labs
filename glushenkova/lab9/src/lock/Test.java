package lock;

public class Test
{
	public static void main(String[] args)
	{
		CalculateWords cw = new CalculateWords(args, args.length);
		cw.findWords();
	}
}