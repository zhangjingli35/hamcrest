<?php
/*  Copyright (c) 2000-2008 hamcrest.org
 */
namespace Hamcrest::Core;

use Hamcrest::BaseMatcher;
use Hamcrest::Description;
use Hamcrest::Matcher;

abstract class ShortcutCombination extends BaseMatcher {
    private $matchers;

    public function __construct($matchers) {
        $this->matchers = $matchers;
    }

    protected function doMatches($o, $shortcut) {
        foreach ($this->matchers as $matcher) {
            if ($matcher->matches($o) == $shortcut) {
                return $shortcut;
            }
        }

        return !$shortcut;
    }

    protected function doDescribeTo(Description $description, $operator) {
        $description->appendList('(', ' ' . $operator . ' ', ')', $matchers);
    }
}
