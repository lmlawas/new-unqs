import java.util.Scanner;

public class UNQS{
	public static void main(String args[]){

		Configuration config = new Configuration();
		Scanner input = new Scanner( System.in );
		String choice, filename = null;
		int read = 0;

		config.setDefault();	
		
		if(args.length == 0){
			do{
				System.out.print("Continue with the default configuration? (Y/N) ");
				choice = input.nextLine();

				/* use default configuration */
				if( choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes") ){					
					break;
				}

				else if(choice.equalsIgnoreCase("n") || choice.equalsIgnoreCase("no")){
					System.out.print("Enter <filename>.conf (must be in same directory of UNQS): ");
					filename = input.nextLine();
					break;
				}

				else{
					System.out.println("Invalid choice. Try again.");
				}
			}while(true);			
		}

		else if(args.length > 1){
			System.out.println("Error. Too many arguments. Please re-run program.");
			return;
		}

		else if(args.length == 1){
			filename = args[0];
		}

		/* configure using .conf file */
		if(filename != null){
			read = config.readFile( filename );			
		}

		if(read == -1){
			return;
		}

		config.show();
	}
}