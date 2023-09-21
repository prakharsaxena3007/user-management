package authz

import future.keywords
import future.keywords.every

default allow :=false

allow if{
	input.method =="GET"
	input.path = ["api","v1","users","getAllUser"]
    	input.role == "client_admin"
	
}
	
allow if{
	input.method =="POST"
	input.path = ["api","v1","users","register"]
	input.role == "client_admin"
	}

allow if{
	input.method =="PUT"
	input.path = ["api","v1","users","update_password"]
	input.role == "client_user"
	}
	
	
