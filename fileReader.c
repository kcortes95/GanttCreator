#include <stdio.h>

int main(void){
	int c;
	FILE *file;
	file = fopen("sample.txt", "r");
	
	int line = 0;

	int generalAlgorithm = 0;
	int ultAlgorithm = 0; 	

	if (file) {
		while ((c = getc(file)) != EOF){

			if(c=='\n')
				line++;

			//se queda siempre con el ultimo numero que encuentra en el archivo
			if(line==0)
				generalAlgorithm = c - '0';

			if(line==1)
				ultAlgorithm = c - '0';			

			if(line != 0 && line != 1){
				putchar(c);
			}

		}
		fclose(file);
	}

	printf("generalAlgorithm: %d \n", generalAlgorithm);
	printf("ultAlgorithm: %d \n", ultAlgorithm);

}
