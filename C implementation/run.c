#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "thread.c"
#include "process.c"
#include "fileReader.c"
#include "macros.c"

int main(void){
	upload_from_file("sample.txt");
}