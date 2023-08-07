package authz

import future.keywords
import future.keywords.every

default allow :=false

allow if{
	input.method =="GET"
	input.path[0] = ["api","v1","users"]
    	input.role == "client_user"
	
}
	
allow if{
	input.method =="POST"
	input.path = ["api","v1","users"]
	input.role == "client_user"
	}

allow if{
	input.method =="PUT"
	input.path = ["api","v1","users"]
	input.role = "client_admin"
	}
	
	
