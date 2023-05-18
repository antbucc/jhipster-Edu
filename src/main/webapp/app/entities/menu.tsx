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
      <MenuItem icon="asterisk" to="/competence">
        <Translate contentKey="global.menu.entities.competence" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/concept">
        <Translate contentKey="global.menu.entities.concept" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/activity">
        <Translate contentKey="global.menu.entities.activity" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/fragment">
        <Translate contentKey="global.menu.entities.fragment" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/precondition">
        <Translate contentKey="global.menu.entities.precondition" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/effect">
        <Translate contentKey="global.menu.entities.effect" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/condition">
        <Translate contentKey="global.menu.entities.condition" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/path">
        <Translate contentKey="global.menu.entities.path" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/goal">
        <Translate contentKey="global.menu.entities.goal" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
