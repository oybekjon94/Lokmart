package com.oybekdev.lokmart.data.store

import javax.inject.Inject

class RecentStore @Inject constructor():BaseStore<Array<String>>("recents", Array<String>::class.java)