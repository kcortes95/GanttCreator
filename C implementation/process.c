#include <stdio.h>
#include "macros.c"

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