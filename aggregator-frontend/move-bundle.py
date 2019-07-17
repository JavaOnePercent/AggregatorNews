import os
import shutil

if os.path.exists('./dist/index-bundle.js') and os.path.exists('./dist/index-bundle.js.map'):
    shutil.copy('./dist/index-bundle.js', '../aggregator-backend/src/main/webapp/static')
    shutil.copy('./dist/index-bundle.js.map', '../aggregator-backend/src/main/webapp/static')