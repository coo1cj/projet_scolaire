<?php
  require_once('myModel.php');
  ini_set('session.cookie_httponly',1 );
  session_start();
  
  // URL de redirection par défaut (si pas d'action ou action non reconnue)
  $url_redirect = "index.php";

  if (isset($_REQUEST['action'])) {
		
      if ($_REQUEST['action'] == 'authenticate') {
          /* ======== AUTHENT ======== */
          if (!isset($_REQUEST['login']) || !isset($_REQUEST['mdp']) || $_REQUEST['login'] == "" || $_REQUEST['mdp'] == "") {
              // manque login ou mot de passe
              $url_redirect = "vw_login.php?nullvalue";  
          } else {		
			  $utilisateur = findUserByLoginPwd($_REQUEST['login'], $_REQUEST['mdp']);		  
              if ($utilisateur == false) {
                // echec authentification
                $url_redirect = "vw_login.php?badvalue";              
              } else {
                // authentification réussie
              $sToken = $_REQUEST['token'];
			  $knownGoodToken = $_SESSION["token"];
		      echo $sToken;
			  if($sToken != $knownGoodToken){
				die('Invalid request');
			  }else{ 
                $_SESSION["connected_user"] = $utilisateur;
                $_SESSION["listeUsers"] = findAllUsers();
                $url_redirect = "vw_moncompte.php";
              }
			  }
          }
      } else if ($_REQUEST['action'] == 'disconnect') {
          /* ======== DISCONNECT ======== */
          unset($_SESSION["connected_user"]);
		  $url_redirect = 'vw_login.php?disconnect';
          //$url_redirect = $_REQUEST['loginPage'] ;
          
      } else if ($_REQUEST['action'] == 'transfert') {
          /* ======== TRANSFERT ======== */
          if (is_numeric ($_REQUEST['montant'])) {
              transfert($_REQUEST['destination'],$_SESSION["connected_user"]["numero_compte"], $_REQUEST['montant']);
              $_SESSION["connected_user"]["solde_compte"] = $_SESSION["connected_user"]["solde_compte"] -  $_REQUEST['montant'];
              $url_redirect = "vw_moncompte.php?trf_ok";
              
          } else {
              $url_redirect = "vw_moncompte.php?bad_mt=".$_REQUEST['montant'];
          }
       
      } else if ($_REQUEST['action'] == 'sendmsg') {
          /* ======== MESSAGE ======== */
		  
          addMessage($_REQUEST['to'],$_SESSION["connected_user"]["id_user"],$_REQUEST['sujet'],$_REQUEST['corps']);
          $url_redirect = "vw_moncompte.php?msg_ok";
              
      } else if ($_REQUEST['action'] == 'msglist') {
          /* ======== MESSAGE ======== */
		  if(!isset($_SESSION["connected_user"]) || $_SESSION["connected_user"] == "") {
			  // utilisateur non connect?
			  $url_redirect = "Location: vw_login.php";      
		  } else {
			  $_SESSION['messagesRecus'] = findMessagesInbox($_REQUEST["userid"]);
			  $url_redirect = "vw_messagerie.php";    
		  }
          
              
      } 

       
  }  
  
  header("Location: $url_redirect");

?>
