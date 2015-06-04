<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<head>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="http://cdn.zingchart.com/zingchart.min.js"></script>


</head>
</head>
<body>
<?php 

    /* Open connection to "zing_db" MySQL database. */
    $mysqli = new mysqli("localhost", "root", "root", "performance_monitor");
 
    /* Check the connection. */
    if (mysqli_connect_errno()) {
        printf("Connect failed: %s\n", mysqli_connect_error());
        exit();
    }
 
    /* Fetch result set from t_test table */
    $data=mysqli_query($mysqli,"SELECT * FROM t_test");
    ?>
    
    <script>
    var myData=[<?php 
    while($info=mysqli_fetch_array($data))
        echo $info['cpuPercent'].','; /* We use the concatenation operator '.' to add comma delimiters after each data value. */
    ?>];
    <?php
    $data=mysqli_query($mysqli,"SELECT * FROM process_info");
    ?>
    var myLabels=[<?php 
    while($info=mysqli_fetch_array($data))
        echo '"'.$info['memUsage'].'",'; /* The concatenation operator '.' is used here to create string values from our database names. */
    ?>];
    </script>
     <?php
    /* Close the connection */
    $mysqli->close(); 
    ?>
    
    
    
    <script type="text/javascript">
    window.onload=function(){
    zingchart.render({
        id:"myChart",
        width:"100%",
        height:400,
        data:{
        "type":"bar",
        "title":{
            "text":"Data Pulled from MySQL Database"
        },
        "scale-x":{
            "labels":myLabels
        },
        "series":[
            {
                "values":myData
            }
    ]
    }
    });
    };
    </script>
</body>
</html>