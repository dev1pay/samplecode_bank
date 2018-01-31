<?php
   $access_key = isset($_GET["access_key"]) ? $_GET["access_key"] : NULL;
   $amount = isset($_GET["amount"]) ? $_GET["amount"] : NULL;
   $card_name = isset($_GET["card_name"]) ? $_GET["card_name"] : NULL;
   $card_type = isset($_GET["card_type"]) ? $_GET["card_type"] : NULL;
   $order_id = isset($_GET["order_id"]) ? $_GET["order_id"] : NULL;
   $order_info = isset($_GET["order_info"]) ? $_GET["order_info"] : NULL;
   $order_type = isset($_GET["order_type"]) ? $_GET["order_type"] : NULL;
   $request_time = isset($_GET["request_time"]) ? $_GET["request_time"] : NULL;
   $response_code = isset($_GET["response_code"]) ? $_GET["response_code"] : NULL;
   $response_message = isset($_GET["response_message"]) ? $_GET["response_message"] : NULL;
   $response_time = isset($_GET["response_time"]) ? $_GET["response_time"] : NULL;
   $trans_ref = isset($_GET["trans_ref"]) ? $_GET["trans_ref"] : NULL;
   $trans_status = isset($_GET["trans_status"]) ? $_GET["trans_status"] : NULL;

   $secret=""; 
   $data_result = "access_key=".$access_key
       ."&amount=".$amount
       ."&card_name=".$card_name
       ."&card_type=".$card_type
       ."&order_id=".$order_id
       ."&order_info=".$order_info
       ."&order_type=".$order_type
       ."&request_time=".$request_time
       ."&response_code=".$response_code
       ."&response_message=".$response_message
       ."&response_time=".$response_time
       ."&trans_ref=".$trans_ref
       ."&trans_status=".$trans_status;

   $signature1 = hash_hmac("sha256", $data_result, $secret);
   $signature = isset($_GET["signature"]) ? $_GET["signature"] : NULL;
  
   if($signature1==$signature)
   {
    if($response_code == "00")
      {

      echo $response_message."-".$amount;
      }
              else 
                  echo $response_message;
    }
   else 
    echo  "Sai chu ki";
  
?>
