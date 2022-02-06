<?php

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

// include database and object files
include_once '../config/database.php';
include_once '../objects/user.php';
 
// get database connection
$database = new Database();
$db = $database->getConnection();
 
// prepare user object
$user = new User($db);
// set ID property of user to be edited
$data = json_decode(file_get_contents("php://input"));

if(!empty($data->username) && !empty($data->password)) {
$user->username = $data->username;
$user->password = base64_encode($data->password);
// read the details of user to be edited
$stmt = $user->login();
if($stmt->rowCount() > 0){
    // get retrieved row
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    // create array
    $user_arr=array(
        "status" => true,
        "message" => "Successfully Login!",
        "id" => $row['id'],
        "username" => $row['username']
    );
    http_response_code(200);    
    echo json_encode($user_arr);    
}
else{
    $user_arr=array(
        "status" => false,
        "message" => "Invalid Username or Password!",
    );
    http_response_code(401);  
    echo json_encode($user_arr);    
}
}
else {
    $user_arr=array(
        "status" => false,
        "message" => "Invalid Request",
    );
    http_response_code(400);  
    echo json_encode($user_arr);    
}
