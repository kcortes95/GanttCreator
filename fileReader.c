#include <stdio.h>

struct Thread{
	int type; //0 ULT ó 1 KLT //By default, 0
	int cpu_io[12];
	int max_cpu_io;
};

struct Process{
	struct Thread threads[3];
	int total_threads;
	int status; //ejecutando, bloqueado
	int remaining_time; //para hacer el SPN
};

void createProcesses(int p, int c, int type_t);

int main(void){
	int c;
	FILE *file;
	file = fopen("sample.txt", "r");
	
	int line = 0;

	int generalAlgorithm = 0;
	int ultAlgorithm = 0; 	
	int cores = 0;
	int total_processes = 0;

	if (file) {
		while ((c = getc(file)) != EOF){

			if(c=='\n')
				line++;

			//se queda siempre con el ultimo numero que encuentra en el archivo
			if(line==0)
				generalAlgorithm = c - '0';

			if(line==1)
				ultAlgorithm = c - '0';			

			if(line==2)
				cores = c - '0';

			if(line==3)
				total_processes = c - '0'; //max: 10

			if(line != 0 && line != 1 && line != 2 && line != 3){
				if(c=='P'){
					int p = (int)getc(file)-'0'; //nro del proceso
					int t = (int)getc(file)-'0'; //nro del hilo
					int type_t = (int)getc(file)-'0'; //si es ult o klt
					createProcesses(p,t, type_t);
				}
			}

		}
		fclose(file);
	}

	printf("\ngeneralAlgorithm: %d \n", generalAlgorithm);
	printf("ultAlgorithm: %d \n", ultAlgorithm);
	printf("cores: %d\n",cores);
	printf("Total processes: %d\n",total_processes);

}


void createProcesses(int p, int t, int type_t){
	char* type = "";
	if(type_t == 0)
		type = "ULT";
	else
		type = "KLT";

	printf("Tomo el proceso %d, hilo %d %s \n", p, t, type);
	//processes[number].status = 1;
}


