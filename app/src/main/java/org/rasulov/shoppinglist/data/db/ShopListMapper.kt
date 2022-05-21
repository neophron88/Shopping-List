package org.rasulov.shoppinglist.data.db

import org.rasulov.shoppinglist.domain.ShopItem

class ShopListMapper {

    fun mapEntityToDBModel(it: ShopItem) =
        ShopItemDBModel(
            it.id,
            it.name,
            it.quantity,
            it.isEnabled
        )

    fun mapDBModelToEntity(it: ShopItemDBModel) =
        ShopItem(
            it.name,
            it.quantity,
            it.isEnabled,
            it.id
        )

    fun mapDBModelListToEntityList(list: List<ShopItemDBModel>) =
        list.map { mapDBModelToEntity(it) }

}