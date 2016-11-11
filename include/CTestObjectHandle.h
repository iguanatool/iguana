/*
 * CTestObjectHandle.h
 * Author: Phil McMinn
 * Date: 08 June 2006
 *
 * Instrumentation and test object calling routines
 */

#ifndef _Included_CTestObjectHandle
#define _Included_CTestObjectHandle

#include <jni.h>

typedef unsigned char Boolean;

// global temporary variable for managing switch expressions
long __ins_switch_temp;

// print native errors and quit
void native_error				(char *error);

// instrumentation functions
Boolean node					(int /* node_id */, Boolean /* condition_outcome */);
Boolean is_true					(int /* condition_id */, double /* a */);
Boolean ref_equals				(int /* condition_id */, void * /* ptr1 */, void * /* ptr2 */);
Boolean ref_not_equals			(int /* condition_id */, void * /* ptr1 */, void * /* ptr2 */);
Boolean equals					(int /* condition_id */, double /* a */,    double /* b */);
Boolean not_equals				(int /* condition_id */, double /* a */,    double /* b */); 
Boolean less_than				(int /* condition_id */, double /* a */,    double /* b */); 
Boolean less_than_or_equal		(int /* condition_id */, double /* a */,    double /* b */); 
Boolean greater_than			(int /* condition_id */, double /* a */,    double /* b */);
Boolean greater_than_or_equal	(int /* condition_id */, double /* a */, 	double /* b */);

// call test object
void initialize_and_call(JNIEnv * /* env */, jobject /* jobj */, jdoubleArray /* args */);

#endif 