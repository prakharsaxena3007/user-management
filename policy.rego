package authz

import future.keywords
import future.keywords.every

default allow :=false

allow if{
	input.method =="GET"
	input.path = ["api","v1","users","all-users"]
    	input.role == "client_admin"
	
}
	
allow if{
	input.method =="POST"
	input.path = ["api","v1","users"]
	input.role == "client_admin"
	}

allow if{
	input.method =="PUT"
	input.path = ["api","v1","users","1"]
	input.role == "client_user"
	}
	
	
