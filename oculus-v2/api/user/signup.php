<?php

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");


// get database connection
include_once '../config/database.php';

// instantiate user object
include_once '../objects/user.php';

$database = new Database();
$db = $database->getConnection();

$user = new User($db);

try 
{
$data = json_decode(file_get_contents("php://input"));

if (!empty($data->username) && !empty($data->password)) {
    // set user property values
    $user->firstname = $data->firstname;
    $user->lastname = $data->lastname;
    $user->email = $data->email;
    $user->mobilenumber = $data->mobilenumber;
    $user->username = $data->username;
    $user->password = base64_encode($data->password);
    $user->created = date('Y-m-d H:i:s');

    // create the user
    if ($user->signup()) {
        $user_arr = array(
            "status" => true,
            "message" => "Successfully Signup!",
            "id" => $user->id,
            "username" => $user->username
        );
        http_response_code(201);
        echo json_encode($user_arr);
    } else {
        $user_arr = array(
            "status" => false,
            "message" => "Username already exists!"
        );
        http_response_code(403);
        echo json_encode($user_arr);
    }
} else {
    $user_arr = array(
        "status" => false,
        "message" => "Invalid Request",
    );
    http_response_code(400);
    echo json_encode($user_arr);
}
}
catch(Exception $e) {
    echo json_encode($e->getMessage());
}
