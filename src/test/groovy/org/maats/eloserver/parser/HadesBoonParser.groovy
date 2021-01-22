package org.maats.eloserver.parser

class HadesBoonParser {


    static void main(String... args) {

        File outputFile = new File('hades/boons.sql')
        if(outputFile.exists()) {
            outputFile.delete()
        }
        outputFile.createNewFile()

        File inputFile = new File('hades/boons.txt')

        inputFile.eachLine { line ->
            if(line && !line.startsWith('##')) {
                def chunks = line.replace("'", '').split('--') as List
                outputFile << """insert into rating.players (id, domain, name, description) values ('${UUID.randomUUID().toString()}', 'hades-boons', '${chunks[0]}', '${chunks[1]}');\n"""
            }
        }

    }
}
