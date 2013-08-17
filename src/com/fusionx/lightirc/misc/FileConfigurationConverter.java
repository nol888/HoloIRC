/*
    HoloIRC - an IRC client for Android

    Copyright 2013 Lalit Maganti

    This file is part of HoloIRC.

    HoloIRC is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    HoloIRC is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with HoloIRC. If not, see <http://www.gnu.org/licenses/>.
 */

package com.fusionx.lightirc.misc;

import android.content.Context;
import android.content.SharedPreferences;

import com.fusionx.irc.ServerConfiguration;

public class FileConfigurationConverter {
    public static ServerConfiguration.Builder convertFileToBuilder(final Context context,
                                                                   final String filename) {
        final SharedPreferences serverSettings = context.getSharedPreferences(filename,
                Context.MODE_PRIVATE);
        final ServerConfiguration.Builder builder = new ServerConfiguration.Builder();
        builder.setTitle(serverSettings.getString(PreferenceKeys.Title, ""));
        builder.setUrl(serverSettings.getString(PreferenceKeys.URL, ""));
        builder.setPort(Integer.parseInt(serverSettings.getString(PreferenceKeys.Port, "6667")));
        builder.setSsl(serverSettings.getBoolean(PreferenceKeys.SSL, false));

        builder.setNick(serverSettings.getString(PreferenceKeys.Nick, ""));
        builder.setRealName(serverSettings.getString(PreferenceKeys.RealName, "HoloIRC"));
        //builder.setAutoNickChange(serverSettings.getBoolean(PreferenceKeys.AutoNickChange, true));

        //final Set<String> auto = serverSettings.getStringSet(PreferenceKeys.AutoJoin, new HashSet<String>());
        //for (final String channel : auto) {
        //builder.addAutoJoinChannel(channel);
        //}

        builder.setServerUserName(serverSettings.getString(PreferenceKeys.ServerUserName, "holoirc"));
        builder.setServerPassword(serverSettings.getString(PreferenceKeys.ServerPassword, ""));

        final String nickServPassword = serverSettings.getString(PreferenceKeys.NickServPassword, null);
        if (nickServPassword != null && !nickServPassword.equals("")) {
            builder.setNickservPassword(nickServPassword);
        }

        builder.setFile(filename);
        return builder;
    }
}