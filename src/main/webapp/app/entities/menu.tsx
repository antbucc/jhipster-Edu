import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/domain">
        <Translate contentKey="global.menu.entities.domain" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/scenario">
        <Translate contentKey="global.menu.entities.scenario" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/module">
        <Translate contentKey="global.menu.entities.module" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/learner">
        <Translate contentKey="global.menu.entities.learner" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/educator">
        <Translate contentKey="global.menu.entities.educator" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
