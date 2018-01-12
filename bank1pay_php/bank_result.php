<?php
function execPostRequest($url, $data)
  {
  $trans_ref = isset($_GET["trans_ref"]) ? $_GET["trans_ref"] : NULL;
  $response_code = isset($_GET["response_code"]) ? $_GET["response_code"] : NULL;

  $access_key = "";           // require your access key from 1pay
  $secret = "";               // require your secret key from 1pay
  $return_url = "https://localhost/bank_result.php"; // returl url

    $response_message = $decode_bankCharging["response_message"];
   $response_code = $decode_bankCharging["response_code"];
   $amount = $decode_bankCharging["amount"];

   if($response_code == "00")
   {
    echo $response_message."-".$amount;
   }
            else
                echo $response_message;
  }
  
?>
