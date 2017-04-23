#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_PROS 10
#define MAX_THREAD 3
#define EXTRA_UPPER_LINES_TXT 5
#define MAX_COLS 15
#define MAX_CHAR_PER_LINE 256
#define NUMBER_OF_COL_START_CPU_IO 5
#define NUMBER_OF_COL_THREAD_TYPE 2

struct Thread{
	int type; //0 ULT ó 1 KLT //By default, 0
	int cpu_io[12];
};

struct Process{
	struct Thread threads[MAX_THREAD];
	int id;
	int total_threads; //ver como lo integro con el resto, si no es necesario, borrarlo
	int status; //ejecutando, bloqueado
	int remaining_time; //tiempo para terminar todo el proceso
	int remaining_time_to_free; //tiempo que le falta para desbloquearse, en el caso que esté bloqueado
	int arrival_time;
	int designated_core;
};

struct Process processes[MAX_PROS];

void upload_from_file(char* file_path);
void read_by_line(char* all[], char* path);
int string_parser(char* storiginal, int to_ret[]);
int string_to_int(char* string);

int main(void){
	upload_from_file("sample.txt");
}

void upload_from_file(char* file_path){
	int c;
	//char* file_path = "sample.txt";

	// generalAlgorithm 0
	// ultAlgorithm 1 	
	// cores 2
	// total_processes 3
	int numbers_by_user[EXTRA_UPPER_LINES_TXT];

	char* strings[MAX_PROS * MAX_THREAD + EXTRA_UPPER_LINES_TXT];
	read_by_line(strings, file_path);

	for(int i = 0 ; i < EXTRA_UPPER_LINES_TXT ; i++){
		numbers_by_user[i] = string_to_int(strings[i]);
		printf("%d\n",numbers_by_user[i]);
	}

	//el 11 esta hardcodeado
	for(int i = EXTRA_UPPER_LINES_TXT; i < numbers_by_user[4] + EXTRA_UPPER_LINES_TXT; i++){
		int values[MAX_COLS];
		string_parser(strings[i],values);
		
		processes[values[0]].id=values[0];
		processes[values[0]].remaining_time_to_free = -1; //no comienza bloqueado
		processes[values[0]].arrival_time = values[4];
		processes[values[0]].designated_core = -1; //not available yet

		for(int p = 0 ; p < values[3] - 1; p++){
			processes[values[0]].threads[values[1]].cpu_io[p] = values[p + NUMBER_OF_COL_START_CPU_IO];
		}
		processes[values[0]].threads[values[1]].type = values[NUMBER_OF_COL_THREAD_TYPE];


		for(int j = 0 ; j < 4 + values[3] ; j++){
			printf("%d ",values[j]);
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