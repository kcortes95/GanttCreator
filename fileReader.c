#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_PROC 10
#define MAX_THREAD 3
#define EXTRA_LINES_TXT 4
#define MAX_COLS 15
#define MAX_CHAR_PER_LINE 256

struct Thread{
	int type; //0 ULT ó 1 KLT //By default, 0
	int arrival_time;
	int cpu_io[12];
};

struct Process{
	struct Thread threads[3];
	int id;
	int total_threads;
	int status; //ejecutando, bloqueado
	int remaining_time; //para hacer el SPN
};

void read_by_line(char* all[], char* path);
int string_parser(char* storiginal, int to_ret[]);
int string_to_int(char* string);

int main(void){
	int c;
	char* file_path = "sample.txt";

	// generalAlgorithm 0
	// ultAlgorithm 1 	
	// cores 2
	// total_processes 3
	int numbers_by_user[EXTRA_LINES_TXT];

	char* strings[MAX_PROC * MAX_THREAD + EXTRA_LINES_TXT];
	read_by_line(strings, file_path);

	for(int i = 0 ; i < EXTRA_LINES_TXT ; i++){
		numbers_by_user[i] = string_to_int(strings[i]);
		printf("%d\n",numbers_by_user[i]);
	}

	//el 11 esta hardcodeado
	for(int i = EXTRA_LINES_TXT; i < 11 ; i++){
		int values[MAX_COLS];
		string_parser(strings[i],values);
		for(int j = 0 ; j < MAX_COLS ; j++){
			printf("%d-",values[j]);
		}
		printf("\n");
	}

	
}

void read_by_line(char* all[], char* path)
{
	FILE *stream;
	char *line = NULL;
	size_t len = 0;
	ssize_t read;
	//char* all[30]; 

	stream = fopen(path, "r");
	if (stream == NULL)
		exit(EXIT_FAILURE);
 
	int counter=0;
	while ((read = getline(&line, &len, stream)) != -1) {
		all[counter] = malloc(read*sizeof(char));		
		strcpy(all[counter],line);
		counter++;	
	}
 
	free(line);
	fclose(stream);
	return;
}

int string_to_int(char* string)
{
	 return atoi(string);
}

//devuelve la cantidad de columnas
int string_parser(char* storiginal, int to_ret[])
{
	char st[MAX_CHAR_PER_LINE];
	strcpy(st,storiginal);	

	char *ch;
	ch = strtok(st, " ");
	int counter = 0;

	while (ch != NULL) {
		to_ret[counter] = string_to_int(ch);
		counter++;		
		ch = strtok(NULL, " ");
	}
	return counter;
}