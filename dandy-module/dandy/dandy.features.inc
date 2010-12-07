<?php

/**
 * Implementation of hook_imagecache_default_presets().
 */
function dandy_imagecache_default_presets() {
  $items = array(
    'w320' => array(
      'presetname' => 'w320',
      'actions' => array(
        '0' => array(
          'weight' => '0',
          'module' => 'imagecache',
          'action' => 'imagecache_scale',
          'data' => array(
            'width' => '320',
            'height' => '',
            'upscale' => 1,
          ),
        ),
      ),
    ),
    'w360' => array(
      'presetname' => 'w360',
      'actions' => array(
        '0' => array(
          'weight' => '0',
          'module' => 'imagecache',
          'action' => 'imagecache_scale',
          'data' => array(
            'width' => '360',
            'height' => '',
            'upscale' => 1,
          ),
        ),
      ),
    ),
    'w480' => array(
      'presetname' => 'w480',
      'actions' => array(
        '0' => array(
          'weight' => '0',
          'module' => 'imagecache',
          'action' => 'imagecache_scale',
          'data' => array(
            'width' => '480',
            'height' => '',
            'upscale' => 1,
          ),
        ),
      ),
    ),
  );
  return $items;
}

/**
 * Implementation of hook_views_api().
 */
function dandy_views_api() {
  return array(
    'api' => '2',
  );
}
