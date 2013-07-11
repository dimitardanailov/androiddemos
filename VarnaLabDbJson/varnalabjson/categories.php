<?php

include_once 'connection.php';

$query = mysql_query('SELECT * FROM categories ORDER BY id ASC');

while ($record = mysql_fetch_assoc($query))
{
    $output[] = $record;
}

print(json_encode($output));

mysql_close();
?>
