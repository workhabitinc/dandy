<?php
function dandy_taxonomy_dictionary($vid = '') {
  $terms = taxonomy_get_tree($vid);
  $tids = array();
  foreach ($terms as $term) {
    $tids[$term->tid] = $term;
  }
  $query = 'SELECT tid, COUNT(nid) as node_count FROM {term_node} WHERE tid IN (' . implode(',', array_keys($tids)) . ') GROUP BY tid';
  $resultset = db_query($query);
  while ($row = db_fetch_array($resultset)) {
    $tids[$row['tid']]->node_count = $row['node_count'];
  }
  return array_values($tids);
}

function dandy_file_getdirectorypath() {
  return file_directory_path();
}