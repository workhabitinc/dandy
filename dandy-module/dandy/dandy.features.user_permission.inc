<?php

/**
 * Implementation of hook_user_default_permissions().
 */
function dandy_user_default_permissions() {
  $permissions = array();

  // Exported permission: access comments from remote
  $permissions['access comments from remote'] = array(
    'name' => 'access comments from remote',
    'roles' => array(
      '0' => 'anonymous user',
    ),
  );

  // Exported permission: access content
  $permissions['access content'] = array(
    'name' => 'access content',
    'roles' => array(
      '0' => 'anonymous user',
      '1' => 'authenticated user',
    ),
  );

  // Exported permission: access file directory path from remote
  $permissions['access file directory path from remote'] = array(
    'name' => 'access file directory path from remote',
    'roles' => array(
      '0' => 'anonymous user',
      '1' => 'authenticated user',
    ),
  );

  // Exported permission: access taxonomy from remote
  $permissions['access taxonomy from remote'] = array(
    'name' => 'access taxonomy from remote',
    'roles' => array(
      '0' => 'anonymous user',
      '1' => 'authenticated user',
    ),
  );

  // Exported permission: get any user data
  $permissions['get any user data'] = array(
    'name' => 'get any user data',
    'roles' => array(),
  );

  // Exported permission: get available services
  $permissions['get available services'] = array(
    'name' => 'get available services',
    'roles' => array(
      '0' => 'anonymous user',
      '1' => 'authenticated user',
    ),
  );

  // Exported permission: save comments from remote
  $permissions['save comments from remote'] = array(
    'name' => 'save comments from remote',
    'roles' => array(
      '0' => 'anonymous user',
    ),
  );

  // Exported permission: save file information
  $permissions['save file information'] = array(
    'name' => 'save file information',
    'roles' => array(
      '0' => 'anonymous user',
    ),
  );

  // Exported permission: view imagecache w320
  $permissions['view imagecache w320'] = array(
    'name' => 'view imagecache w320',
    'roles' => array(),
  );

  // Exported permission: view imagecache w360
  $permissions['view imagecache w360'] = array(
    'name' => 'view imagecache w360',
    'roles' => array(),
  );

  // Exported permission: view imagecache w480
  $permissions['view imagecache w480'] = array(
    'name' => 'view imagecache w480',
    'roles' => array(),
  );

  return $permissions;
}
