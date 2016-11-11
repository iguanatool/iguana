/*
 * CTestObjectHandle.c
 * Author: Phil McMinn
 * Date: 08 June 2006
 *
 */

#include <stdlib.h>
#include <setjmp.h>
#include "CTestObjectHandle.h"
#include "CTestObjectImpl.h"

long __ins_switch_temp = -1;
static jmp_buf __ins_early_exit_temp;

void native_error(char *error) 
{
	printf("Error occurred in JNI-called code: %s \n", error);
	exit(1);
}

/* local IDs */
static JNIEnv    *genv;
static jclass    test_object_handle_class;
static jclass    trace_class;
static jobject   trace_object;
static jfieldID  trace_field_id;
static jmethodID node_method_id;
static jmethodID is_true_method_id;
static jmethodID ref_equals_method_id;
static jmethodID ref_not_equals_method_id;
static jmethodID equals_method_id;
static jmethodID not_equals_method_id;
static jmethodID less_than_method_id;
static jmethodID less_than_or_equal_method_id;
static jmethodID greater_than_method_id;
static jmethodID greater_than_or_equal_method_id;

/* wrapper functions for Trace Java object */
Boolean node(int node_id, Boolean condition_outcome)
{
	return (*genv)->CallBooleanMethod(genv, trace_object, node_method_id, node_id, condition_outcome);
}
Boolean is_true(int id, double a) 
{
	return (*genv)->CallBooleanMethod(genv, trace_object, is_true_method_id, id, a);
}

Boolean ref_equals(int id, void *ptr1, void *ptr2)
{
	return (*genv)->CallBooleanMethod(genv, trace_object, ref_equals_method_id, id, ptr1 == ptr2);
}

Boolean ref_not_equals(int id, void *ptr1, void *ptr2)
{
	return (*genv)->CallBooleanMethod(genv, trace_object, ref_not_equals_method_id, id, ptr1 == ptr2);
}

Boolean equals(int id, double a, double b) 
{
	return (*genv)->CallBooleanMethod(genv, trace_object, equals_method_id, id, a, b);
}

Boolean not_equals(int id, double a, double b) 
{
	return (*genv)->CallBooleanMethod(genv, trace_object, not_equals_method_id, id, a, b);
}

Boolean less_than(int id, double a, double b) 
{
	return (*genv)->CallBooleanMethod(genv, trace_object, less_than_method_id, id, a, b);
}

Boolean less_than_or_equal(int id, double a, double b) 
{
	return (*genv)->CallBooleanMethod(genv, trace_object, less_than_or_equal_method_id, id, a, b);
}

Boolean greater_than(int id, double a, double b) 
{
	return (*genv)->CallBooleanMethod(genv, trace_object, greater_than_method_id, id, a, b);
}

Boolean greater_than_or_equal(int id, double a, double b) 
{
	return (*genv)->CallBooleanMethod(genv, trace_object, greater_than_or_equal_method_id, id, a, b);
}

void early_exit()
{
	longjmp(__ins_early_exit_temp, 1);
}

void initialize_and_call(JNIEnv * env, jobject jobj, jdoubleArray args) 
{
    jdouble *double_array;
    int num_args;
	jfieldID fid; 
     			
	genv = env;			

	if (test_object_handle_class == NULL) {
		test_object_handle_class = (*env)->GetObjectClass(env, jobj);
		if (test_object_handle_class == NULL) {
			native_error("could not get test object class (TestObject)");
		}
	}
	
	if (trace_field_id == NULL) {
		trace_field_id = (*env)->GetFieldID(env, test_object_handle_class, "t",  "Liguana/testobject/trace/Trace;");
		if (trace_field_id == NULL) {
			native_error("could not get trace field ID of TestObject");
		}	
  	}

	trace_object = (*env)->GetObjectField(env, jobj, trace_field_id);
	if (trace_object == NULL) {
		native_error("could not get trace field of TestObject instance (object not initialized?)");
	}	
	
	if (trace_class == NULL) {
		trace_class = (*env)->GetObjectClass(env, trace_object);
		if (trace_class == NULL) {
			native_error("could not get trace class (Trace)");
		}
		
		node_method_id = (*genv)->GetMethodID(genv, trace_class, "node", "(IZ)Z");			
		if (node_method_id == NULL) {
			native_error("could not get Trace.node() method ID");
		}
		is_true_method_id = (*genv)->GetMethodID(genv, trace_class, "isTrue", "(ID)Z");			
		if (is_true_method_id == NULL) {
			native_error("could not get Trace.isTrue() method ID");
		}
		ref_equals_method_id = (*genv)->GetMethodID(genv, trace_class, "refEquals", "(IZ)Z");			
		if (ref_equals_method_id == NULL) {		
			native_error("could not get Trace.refEquals() method ID");
		}	
		ref_not_equals_method_id = (*genv)->GetMethodID(genv, trace_class, "refNotEquals", "(IZ)Z");			
		if (ref_not_equals_method_id == NULL) {		
			native_error("could not get Trace.refNotEquals() method ID");
		}	
		equals_method_id = (*genv)->GetMethodID(genv, trace_class, "equals", "(IDD)Z");			
		if (equals_method_id == NULL) {
			native_error("could not get Trace.equals() method ID");
		}				
		not_equals_method_id = (*genv)->GetMethodID(genv, trace_class, "notEquals", "(IDD)Z");			
		if (not_equals_method_id == NULL) {
			native_error("could not get Trace.notEquals() method ID");
		}						
		less_than_method_id = (*genv)->GetMethodID(genv, trace_class, "lessThan", "(IDD)Z");			
		if (less_than_method_id == NULL) {
			native_error("could not get Trace.lessThan() method ID");
		}								
		less_than_or_equal_method_id = (*genv)->GetMethodID(genv, trace_class, "lessThanOrEqual", "(IDD)Z");			
		if (less_than_or_equal_method_id == NULL) {
			native_error("could not get Trace.lessThanOrEqual() method ID");
		}										
		greater_than_method_id = (*genv)->GetMethodID(genv, trace_class, "greaterThan", "(IDD)Z");			
		if (greater_than_method_id == NULL) {
			native_error("could not get Trace.greaterThan() method ID");
		}												
		greater_than_or_equal_method_id = (*genv)->GetMethodID(genv, trace_class, "greaterThanOrEqual", "(IDD)Z");			
		if (greater_than_or_equal_method_id == NULL) {
			native_error("could not get Trace.greaterThanOrEqual() method ID");
		}
	}

	double_array = (*env)->GetDoubleArrayElements(env, args, NULL);
    if (double_array == NULL) {
    	native_error("could not convert test object arguments to native double array");
    }
    
	num_args = (*env)->GetArrayLength(env, args);
		
	if (!setjmp(__ins_early_exit_temp)) {
		perform_call(double_array, num_args);	
	}
    (*env)->ReleaseDoubleArrayElements(env, args, double_array, 0);	
}
